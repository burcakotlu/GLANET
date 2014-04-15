package ui;

import java.io.File;

import javax.swing.*;

import common.Commons;

import java.awt.*;              //for layout managers and more
import java.awt.event.*;

public class MainView extends JPanel{

	private MainViewDelegate delegate;
	private JTextField jobName;
	private JTextField outputTextField;
	private JTextField inputTextField;
	private JTextField falseDiscoveryRate;
	private JTextField signifanceCriteria;
	private JTextField numberOfBases;
	private JLabel currentWorkLabel;
	private JPanel listPane;
	private JButton runButton;
	private JComboBox<String> generateRandomDataModeCombo;
	private JComboBox<String> multipleTestingCombo;
	private JComboBox<String> numberOfPerCombo;
	private JComboBox<String> inputFormatCombo;
	private JCheckBox enrichmentOptionsCheck;
	private JCheckBox regulatorySequenceAnalysisUsingRSATCheck;
	private JCheckBox writeGeneratedRandomData;
	private JCheckBox writePermutationBasedAndParametricBased;
	private JCheckBox writePermutationBasedAnnotationResult;
	private JCheckBox dnaseEnrichment;
	private JCheckBox histoneEnrichment;
	private JCheckBox tfAndKeggPathwayEnrichment;
	private JCheckBox tfEnrichment;
	private JCheckBox keggPathwayEnrichment;
	private JCheckBox cellLineBasedTfAndKeggPathwayEnrichment;
	
	
	public interface MainViewDelegate {
		
		public void startRunActionsWithOptions(String inputFileName, 
											   String outputFolder,
											   String inputFileFormat,
											   String numberOfPermutations,
											   String falseDiscoveryRate,
											   String generateRandomDataMode,
											   String writeGeneratedRandomDataMode,
											   String writePermutationBasedandParametricBasedAnnotationResultMode,
											   String writePermutationBasedAnnotationResultMode,
											   String dnaseEnrichment,
											   String histoneEnrichment,
											   String tfAndKeggPathwayEnrichment,
											   String cellLineBasedTfAndKeggPathwayEnrichment,
											   String jobName,
											   String multipleTestingChoice,
											   String bonferoniCorrectionSignificanceLevel);
	}
	
	private ActionListener chooseFilePressed = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fc = new JFileChooser();
			if( e.getActionCommand() == "Glanet Folder")
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			else if( e.getActionCommand() == "Input File Name")
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			int returnVal = fc.showOpenDialog(MainView.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            //This is where a real application would open the file.
	            if( e.getActionCommand() == "Glanet Folder")
	            	outputTextField.setText( file.getPath() + System.getProperty("file.separator"));
	            else if( e.getActionCommand() == "Input File Name")
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
													outputTextField.getText(),
													inputFormatCombo.getSelectedItem().toString(),
													numberOfPerCombo.getSelectedItem().toString(),
													falseDiscoveryRate.getText(),
													generateRandomDataModeCombo.getSelectedItem().toString(),
													writeGeneratedRandomData.isSelected()?Commons.WRITE_GENERATED_RANDOM_DATA:Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA,
													writePermutationBasedAndParametricBased.isSelected()?Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT:Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT,
													writePermutationBasedAnnotationResult.isSelected()?Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT:Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT,
													dnaseEnrichment.isSelected()?Commons.DO_DNASE_ENRICHMENT:Commons.DO_NOT_DNASE_ENRICHMENT,
													histoneEnrichment.isSelected()?Commons.DO_HISTONE_ENRICHMENT:Commons.DO_NOT_HISTONE_ENRICHMENT,
													tfAndKeggPathwayEnrichment.isSelected()?Commons.DO_TF_KEGGPATHWAY_ENRICHMENT:Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT,
													cellLineBasedTfAndKeggPathwayEnrichment.isSelected()?Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT:Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,
													jobName.getText(),
													multipleTestingCombo.getSelectedItem().toString().equalsIgnoreCase("Bonferroni Correction")?Commons.BONFERRONI_CORRECTED_P_VALUE:Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE,
													signifanceCriteria.getText());
			}
		}
	};
	
	ItemListener enableEnrichmentListener = new ItemListener() {
	      public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  enableEnrichmentOptions( enrichmentOptionsCheck.isSelected());
	      }
	};
	
	public MainView() {
		
		currentWorkLabel = new JLabel("Current running process will be shown there");
		inputTextField = new JTextField(30);
		outputTextField = new JTextField(50);
		numberOfBases = new JTextField(30);
		runButton = new JButton("Run");
		runButton.addActionListener(runButtonPressed);
		
		JPanel inputBrowseAndOptionPane = new JPanel();
		JPanel inputBrowseFilePane = createBrowseFileArea( "Input File Name", inputTextField);
		JPanel outputBrowseFilePane = createBrowseFileArea( "Glanet Folder", outputTextField);
		jobName = new JTextField();
		signifanceCriteria = new JTextField(30);
		falseDiscoveryRate = new JTextField(30);
		falseDiscoveryRate.setText("0.05");
		signifanceCriteria.setText("0.05");
		numberOfBases.setText("1");
		
		JPanel fdrAndSigCriteria = new JPanel( new FlowLayout(FlowLayout.LEFT));
		fdrAndSigCriteria.add( createBorderedPanel( "False Discovery Rate", falseDiscoveryRate));
		fdrAndSigCriteria.add( createBorderedPanel( "Bonferroni Correction Significance Criteria", signifanceCriteria));
		
		listPane = new JPanel();
		
		listPane.setLayout( new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		inputBrowseAndOptionPane.setLayout( new FlowLayout(FlowLayout.LEFT));
		
		String[] generateRandomDataModeSet = { "With GC and Mapability", "Without GC and Mapability" };
		String[] numberOfPermutations = { "5000", "10000", "50000", "100000" };
		
		String[] inputFormat = { 	Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE, 
									Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE,
									Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
									Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
									Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE};
		
		String[] multipleTest = { "Benjamini Hochberg FDR", "Bonferroni Correction"};
		
		generateRandomDataModeCombo = new JComboBox<String>( generateRandomDataModeSet);
		numberOfPerCombo = new JComboBox<String>( numberOfPermutations);
		inputFormatCombo = new JComboBox<String>( inputFormat);
		multipleTestingCombo = new JComboBox<String>( multipleTest);
		
		inputBrowseAndOptionPane.add( inputBrowseFilePane);
		inputBrowseAndOptionPane.add( createBorderedPanel( "Input Format", inputFormatCombo));
		
		//annotation panel
		JPanel annotationPanel = new JPanel( new GridLayout( 0, 1));
		JPanel numberOfBasesPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
		JLabel numberOfBasesLabel = new JLabel( "Number of Bases");
		JLabel overlapDefinitionLabel = new JLabel( "Overlap Definition");
		numberOfBasesPanel.add( numberOfBasesLabel);
		numberOfBasesPanel.add( numberOfBases);
		annotationPanel.add( overlapDefinitionLabel);
		annotationPanel.add( numberOfBasesPanel);
		
		listPane.add( inputBrowseAndOptionPane);
		listPane.add( outputBrowseFilePane);
		listPane.add( createBorderedPanel( "Annotation", annotationPanel));
        
        //Checkbuttons
        writeGeneratedRandomData = new JCheckBox( "writeGeneratedRandomData");
        writePermutationBasedAndParametricBased = new JCheckBox( "writePermutationBasedAndParametricBased");
        writePermutationBasedAnnotationResult = new JCheckBox( "writePermutationBasedAnnotationResult");
        dnaseEnrichment = new JCheckBox( "Dnase Enrichment");
        histoneEnrichment = new JCheckBox( "Histone Enrichment");
        tfAndKeggPathwayEnrichment = new JCheckBox( "Tf And KEGG Pathway Enrichment");
        tfEnrichment = new JCheckBox( "Tf Enrichment");
        keggPathwayEnrichment = new JCheckBox( "KEGG Pathway Enrichment");
        cellLineBasedTfAndKeggPathwayEnrichment = new JCheckBox( "CellLine Based Tf And Kegg Pathway Enrichment");
        
        JPanel checkPanel = new JPanel( new GridLayout(0, 1));
        checkPanel.add( writeGeneratedRandomData);
        checkPanel.add( writePermutationBasedAndParametricBased);
        checkPanel.add( writePermutationBasedAnnotationResult);
        
        JPanel enrichmentOptions = new JPanel( new GridLayout(0, 1));
        enrichmentOptions.add( dnaseEnrichment);
        enrichmentOptions.add( histoneEnrichment);
        enrichmentOptions.add( tfEnrichment);
        enrichmentOptions.add( keggPathwayEnrichment);
        enrichmentOptions.add( tfAndKeggPathwayEnrichment);
        enrichmentOptions.add( cellLineBasedTfAndKeggPathwayEnrichment);
        
        JPanel RSATOption = new JPanel( new GridLayout(0, 1));
        regulatorySequenceAnalysisUsingRSATCheck = new JCheckBox( "Regulatory Sequence Analysis Using RSAT");
        RSATOption.add( regulatorySequenceAnalysisUsingRSATCheck);
        
        JPanel enrichmentPanel = new JPanel();
        enrichmentPanel.setLayout( new BoxLayout(enrichmentPanel, BoxLayout.PAGE_AXIS));
        
        enrichmentOptionsCheck = new JCheckBox( "Enable Enrichment");
        enrichmentOptionsCheck.addItemListener( enableEnrichmentListener);
        enableEnrichmentOptions( enrichmentOptionsCheck.isSelected());
        
        enrichmentPanel.add( enrichmentOptionsCheck);
        enrichmentPanel.add( createBorderedPanel( "Generate Random Data Mode", generateRandomDataModeCombo));
        enrichmentPanel.add( createBorderedPanel( "Multiple Testing", multipleTestingCombo));
        enrichmentPanel.add( fdrAndSigCriteria);
        enrichmentPanel.add( createBorderedPanel( "Number of Permutations", numberOfPerCombo));
        enrichmentPanel.add( createBorderedPanel( "Enrichment Options", enrichmentOptions));
        enrichmentPanel.add( createBorderedPanel( "RSAT", RSATOption));
        
        listPane.add( createBorderedPanel( "Enrichment", enrichmentPanel));
        listPane.add( createBorderedPanel( "Write Permutation Results", checkPanel));
        listPane.add( createBorderedPanel( "Job Name", jobName));
        listPane.add( runButton);
        listPane.add( currentWorkLabel);
        
        //scroll pane
        JScrollPane scrollPane = new JScrollPane( listPane);
        scrollPane.setPreferredSize( new Dimension( 860, 720));
        add( scrollPane);
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
	
	    JLabel[] labels = { componentLabel};
	    JComponent[] components = { panel};
	    addLabelTextRows( labels, components, gridbag, paneControlsPane);
	
	    c.gridwidth = GridBagConstraints.REMAINDER; //last
	    c.anchor = GridBagConstraints.WEST;
	    c.weightx = 1.0;
	    paneControlsPane.setBorder(
	            BorderFactory.createCompoundBorder(
	                            BorderFactory.createTitledBorder(borderName),
	                            BorderFactory.createEmptyBorder(5,5,5,5)));
	
	    //Put everything together.
	    JPanel borderedPanel = new JPanel(new BorderLayout());
	    borderedPanel.add(paneControlsPane,
	                 BorderLayout.PAGE_START);
	    
	    return borderedPanel;
	}
	JPanel createBrowseFileArea( String fileType, JTextField textField){
	    
	    JButton browseButton = new JButton( "Browse");
	    browseButton.addActionListener( chooseFilePressed);
	    browseButton.setActionCommand( fileType);
	    
	    JPanel browsePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    browsePanel.add( browseButton);
	    browsePanel.add( textField);
	    
	    return createBorderedPanel(fileType, browsePanel);
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
	
	public void setCurrentProcessInfo( String info){
		
		System.out.println(info);
		currentWorkLabel.setText( info);
	}
	
	public void enableEnrichmentOptions( boolean shouldEnable){
		
		generateRandomDataModeCombo.setEnabled( shouldEnable);
		multipleTestingCombo.setEnabled( shouldEnable);
		falseDiscoveryRate.setEnabled( shouldEnable);
		signifanceCriteria.setEnabled( shouldEnable);
		numberOfPerCombo.setEnabled( shouldEnable);
		dnaseEnrichment.setEnabled( shouldEnable);
		histoneEnrichment.setEnabled( shouldEnable);
		tfEnrichment.setEnabled( shouldEnable);
		keggPathwayEnrichment.setEnabled( shouldEnable);
		tfAndKeggPathwayEnrichment.setEnabled( shouldEnable);
		cellLineBasedTfAndKeggPathwayEnrichment.setEnabled( shouldEnable);
		regulatorySequenceAnalysisUsingRSATCheck.setEnabled( shouldEnable);
		writeGeneratedRandomData.setEnabled( shouldEnable);
		writePermutationBasedAndParametricBased.setEnabled( shouldEnable);
		writePermutationBasedAnnotationResult.setEnabled( shouldEnable);
		
	}
}
