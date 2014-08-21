package ui;

import java.io.File;

import javax.swing.*;
import javax.xml.ws.RequestWrapper;

import common.Commons;

import java.awt.*;              //for layout managers and more
import java.awt.event.*;

public class MainView extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainViewDelegate delegate;
	private JTextField jobName;
	private JTextField outputTextField;
	private JTextField inputTextField;
	private JTextField falseDiscoveryRate;
	private JTextField signifanceCriteria;
	private JTextField numberOfBases;
	private JTextField numberOfPerInEachRun;
	private JLabel currentWorkLabel;
	private JPanel listPane;
	private JButton runButton;
	private JButton stopButton;
	private JComboBox<String> generateRandomDataModeCombo;
	private JComboBox<String> multipleTestingCombo;
	private JComboBox<String> numberOfPerCombo;
	private JComboBox<String> inputFormatCombo;
	private JCheckBox performEnrichmentCheckBox;
	private JCheckBox regulatorySequenceAnalysisUsingRSATCheck;
	private JCheckBox dnaseEnrichment;
	private JCheckBox histoneEnrichment;
	private JCheckBox tfAndKeggPathwayEnrichment;
	private JCheckBox tfEnrichment;
	private JCheckBox keggPathwayEnrichment;
	private JCheckBox cellLineBasedTfAndKeggPathwayEnrichment;
	private JTextArea logArea;
	private JList<String> cellLinesList;
	
	public interface MainViewDelegate {
		
		public void startRunActionsWithOptions(String inputFileName, 
											   String outputFolder,
											   String inputFileFormat,
											   String numberOfBases,
											   String enrichmentEnabled,
											   String generateRandomDataMode,
											   String multipleTestingChoice,
											   String bonferoniCorrectionSignificanceLevel,
											   String falseDiscoveryRate,
											   String numberOfPermutations,
											   String dnaseEnrichment,
											   String histoneEnrichment,
											   String tfEnrihment,
											   String keggPathwayEnrichment,
											   String tfAndKeggPathwayEnrichment,
											   String cellLineBasedTfAndKeggPathwayEnrichment,
											   String regulatorySequenceAnalysisUsingRSAT,
											   String jobName,
											   String writeGeneratedRandomDataMode,
											   String writePermutationBasedandParametricBasedAnnotationResultMode,
											   String writePermutationBasedAnnotationResultMode,
											   String numberOfPermutationsInEachRun,
											   String[] cellLinesToBeConsidered);
		
		public void stopCurrentProcess();
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
				JOptionPane.showMessageDialog(null, "Please fill all the necessary options");
			else {
				
				logArea.setText("");
				logArea.setCaretPosition(logArea.getDocument().getLength());
				
				delegate.startRunActionsWithOptions(
						inputTextField.getText(),
						outputTextField.getText(),
						inputFormatCombo.getSelectedItem().toString(),
						numberOfBases.getText(),
						performEnrichmentCheckBox.isSelected()?Commons.DO_ENRICH:Commons.DO_NOT_ENRICH,
						generateRandomDataModeCombo.getSelectedItem().toString(),
						multipleTestingCombo.getSelectedItem().toString(),
						signifanceCriteria.getText(),
						falseDiscoveryRate.getText(),
						numberOfPerCombo.getSelectedItem().toString(),
						dnaseEnrichment.isSelected()?Commons.DO_DNASE_ENRICHMENT:Commons.DO_NOT_DNASE_ENRICHMENT,
						histoneEnrichment.isSelected()?Commons.DO_HISTONE_ENRICHMENT:Commons.DO_NOT_HISTONE_ENRICHMENT,
						tfEnrichment.isSelected()?Commons.DO_TF_ENRICHMENT:Commons.DO_NOT_TF_ENRICHMENT,
						keggPathwayEnrichment.isSelected()?Commons.DO_GENESET_ENRICHMENT:Commons.DO_NOT_GENESET_ENRICHMENT,
						tfAndKeggPathwayEnrichment.isSelected()?Commons.DO_TF_GENESET_ENRICHMENT:Commons.DO_NOT_TF_GENESET_ENRICHMENT,
						cellLineBasedTfAndKeggPathwayEnrichment.isSelected()?Commons.DO_TF_CELLLINE_GENESET_ENRICHMENT:Commons.DO_NOT_TF_CELLLINE_GENESET_ENRICHMENT,
						regulatorySequenceAnalysisUsingRSATCheck.isSelected()?Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT:Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT,
						jobName.getText(),
						Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA,
						Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT,
						Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT,
						numberOfPerInEachRun.getText(),
						cellLinesList.getSelectedValuesList().toArray(new String[0])
				);
				
				enableStartProcess( false);
			}
		}
	};
	
	private ActionListener stopButtonPressed = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			delegate.stopCurrentProcess();
			
			stopButton.setEnabled( false);
			runButton.setEnabled( true);
		}
	};
	
	ItemListener enableEnrichmentListener = new ItemListener() {
	      public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
	      }
	};
	
	ItemListener enableRegulatorySequenceAnalysis = new ItemListener() {
	      public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  checkUsabilityOfRegulatorySequenceAnalysis();
	      }
	};
	
//	ItemListener adjustTfEnrichmentCheckboxes = new ItemListener() {
//	      public void itemStateChanged(ItemEvent itemEvent) {
//	    	  
//	    	  if( ((JCheckBox)itemEvent.getSource()).getName().equalsIgnoreCase("tfAndKeggPathwayEnrichment")){
//	    		  
//	    		  if( tfAndKeggPathwayEnrichment.isSelected())
//	    			  cellLineBasedTfAndKeggPathwayEnrichment.setSelected( false);
//	    	  } else if (((JCheckBox)itemEvent.getSource()).getName().equalsIgnoreCase("cellLineBasedTfAndKeggPathwayEnrichment")){
//	    		  
//	    		  if( cellLineBasedTfAndKeggPathwayEnrichment.isSelected())
//	    			  tfAndKeggPathwayEnrichment.setSelected( false);
//	    	  }
//	      }
//	};
	
	public MainView() {
		
		//code flow goes respectively with the ui design (top to bottom)
		//you can see the hierarchy by moving down
		
		//holds the general content of the ui. listPane added to scrollPane
		listPane = new JPanel();		
		listPane.setLayout( new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		
		//to enable scroll down on listPane. scrollPane added to this view
		JScrollPane scrollPane = new JScrollPane( listPane);
        scrollPane.setPreferredSize( new Dimension( 940, 720));
		
		//inputBrowseAndOptionPane added to listPane
		JPanel inputBrowseAndOptionPane = new JPanel( new FlowLayout(FlowLayout.LEFT));
		
		//inputTextField added to inputBrowseAndOptionPane
		inputTextField = new JTextField(30);
		inputBrowseAndOptionPane.add( createBrowseFileArea( "Input File Name", inputTextField, Commons.GUI_HINT_INPUT_FILE_NAME));
		
		//inputFormatCombo added to inputBrowseAndOptionPane
		String[] inputFormat = { 	Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE, 
				Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE,
				Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
				Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
				Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE};
		inputFormatCombo = new JComboBox<String>( inputFormat);
		inputBrowseAndOptionPane.add( createBorderedPanel( "Input Format", createPanelWithHint(inputFormatCombo, Commons.GUI_HINT_INPUT_FORMAT)));
		listPane.add( inputBrowseAndOptionPane);
		
		//outputTextField added to listPane
		outputTextField = new JTextField(50);
		listPane.add( createBrowseFileArea( "Glanet Folder", outputTextField, Commons.GUI_HINT_GLANET_FOLDER));
		
		//annotationPanel added to listPane
		JPanel annotationPanel = new JPanel();
		annotationPanel.setLayout( new BoxLayout(annotationPanel, BoxLayout.PAGE_AXIS));
		
		JPanel overlapDefinitionPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
		JLabel overlapDefinitionLabel = new JLabel( "Overlap Definition"); //overlapDefinitionLabel added to annotationPanel
		overlapDefinitionPanel.add( overlapDefinitionLabel);
		annotationPanel.add( overlapDefinitionPanel);
		
		//numberOfBasesPanel added to annotationPanel
		JPanel numberOfBasesPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
		
		//numberOfBasesLabel added to numberOfBasesPanel
		JLabel numberOfBasesLabel = new JLabel( "Number of Bases");
		numberOfBasesPanel.add( numberOfBasesLabel);
		
		//numberOfBases added to numberOfBasesPanel
		numberOfBases = new JTextField(30);
		numberOfBases.setText("1");
		numberOfBasesPanel.add( createPanelWithHint(numberOfBases, Commons.GUI_HINT_NUMBER_OF_BASES));
		annotationPanel.add( numberOfBasesPanel);
		
		//annotationOptions added to annotationPanel
		JPanel annotationOptions = new JPanel( new GridLayout(0, 1));
		
		//dnaseEnrichment added to annotationOptions
		dnaseEnrichment = new JCheckBox( "DNase Enrichment");
		annotationOptions.add( createPanelWithHint(dnaseEnrichment, Commons.GUI_HINT_DNASE_ENRICHMENT));
		
		//histoneEnrichment added to annotationOptions
		histoneEnrichment = new JCheckBox( "Histone Enrichment");
		annotationOptions.add( createPanelWithHint(histoneEnrichment, Commons.GUI_HINT_HISTONE_ENRICHMENT));
		
		//tfEnrichment added to annotationOptions
		tfEnrichment = new JCheckBox( "TF Enrichment");
		tfEnrichment.addItemListener( enableRegulatorySequenceAnalysis);
		annotationOptions.add( createPanelWithHint(tfEnrichment, Commons.GUI_HINT_TF_ENRICHMENT));
		
		//tfEnrichment added to annotationOptions
        keggPathwayEnrichment = new JCheckBox( "KEGG Pathway Enrichment");
        annotationOptions.add( createPanelWithHint(keggPathwayEnrichment, Commons.GUI_HINT_KEGG_PATHWAY_ENRICHMENT));
        
        //tfAndKeggPathwayEnrichment added to annotationOptions
        tfAndKeggPathwayEnrichment = new JCheckBox( "TF And KEGG Pathway Enrichment");
        tfAndKeggPathwayEnrichment.setName( "TFAndKEGGPathwayEnrichment");
        tfAndKeggPathwayEnrichment.addItemListener( enableRegulatorySequenceAnalysis);
        annotationOptions.add( createPanelWithHint(tfAndKeggPathwayEnrichment, Commons.GUI_HINT_TF_AND_KEGG_PATHWAY_ENRICHMENT));
        
        //cellLineBasedTfAndKeggPathwayEnrichment added to annotationOptions
        cellLineBasedTfAndKeggPathwayEnrichment = new JCheckBox( "CellLine Based TF And KEGG Pathway Enrichment");
        cellLineBasedTfAndKeggPathwayEnrichment.setName( "cellLineBasedTfAndKeggPathwayEnrichment");
        cellLineBasedTfAndKeggPathwayEnrichment.addItemListener( enableRegulatorySequenceAnalysis);
        annotationOptions.add( createPanelWithHint( cellLineBasedTfAndKeggPathwayEnrichment, Commons.GUI_HINT_CELLLINE_BASED_TF_AND_KEGG_PATHWAY_ENRICHMENT));
        annotationPanel.add( createBorderedPanel( "Annotation Options", annotationOptions));
        
        //cellLinesScrollPane added to annotationPanel
        cellLinesList = new JList<String>( createCellLines()); //see the comment on the method definition (createCellLines())
        JScrollPane cellLinesScrollPane = new JScrollPane( cellLinesList);
        cellLinesList.setVisibleRowCount( 5);
        annotationPanel.add( createBorderedPanel( "Cell Lines To Be Considered", cellLinesScrollPane));
        
		listPane.add( createBorderedPanel( "Annotation", annotationPanel));
		
		//enrichmenPanel added to listPane
		JPanel enrichmentPanel = new JPanel();
        enrichmentPanel.setLayout( new BoxLayout(enrichmentPanel, BoxLayout.PAGE_AXIS));
        
        //enableEnrichmentCheckBox added to enrichmentPanel
        JPanel performEnrichmentPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
        performEnrichmentCheckBox = new JCheckBox( "Perform Enrichment");
        performEnrichmentCheckBox.addItemListener( enableEnrichmentListener);
        performEnrichmentPanel.add( performEnrichmentCheckBox);
        enrichmentPanel.add( performEnrichmentPanel);
        
        //generateRandomDataModeCombo added to enrichmentPanel
        String[] generateRandomDataModeSet = { Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT,
				Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT };
        generateRandomDataModeCombo = new JComboBox<String>( generateRandomDataModeSet);
        enrichmentPanel.add( createBorderedPanel( "Generate Random Data Mode", createPanelWithHint(generateRandomDataModeCombo, Commons.GUI_HINT_GENERATE_RANDOM_DATA_MODE)));
        
        //multipleTestingCombo added to enrichmentPanel
        String[] multipleTest = { Commons.BENJAMINI_HOCHBERG_FDR, Commons.BONFERRONI_CORRECTION};
        multipleTestingCombo = new JComboBox<String>( multipleTest);
        enrichmentPanel.add( createBorderedPanel( "Multiple Testing", createPanelWithHint(multipleTestingCombo, Commons.GUI_HINT_MULTIPLE_TESTING)));
        
        //fdrAndSigCriteria added to enrichmentPanel
        JPanel fdrAndSigCriteria = new JPanel( new GridLayout(1, 2));
        
        //falseDiscoveryRate added to fdrAndSigCriteria
        falseDiscoveryRate = new JTextField(30);
		falseDiscoveryRate.setText("0.05");
		fdrAndSigCriteria.add( createBorderedPanel( "False Discovery Rate", createPanelWithHint(falseDiscoveryRate, Commons.GUI_HINT_FDR)));
		
		//signifanceCriteria added to fdrAndSigCriteria
		signifanceCriteria = new JTextField(30);
		signifanceCriteria.setText("0.05");
		fdrAndSigCriteria.add( createBorderedPanel( "Bonferroni Correction Significance Criteria", createPanelWithHint(signifanceCriteria, Commons.GUI_HINT_BONFERONI_CORRECTION_SIGNIFICANCE_CRITERIA)));
		enrichmentPanel.add( fdrAndSigCriteria);
		
		//permutationPanel added to enrichmentPanel
		JPanel permutationPanel = new JPanel( new GridLayout(1, 2));
		
		//numberOfPerCombo added to permutationPanel
		String[] numberOfPermutations = { "5000", "10000", "50000", "100000" };
		numberOfPerCombo = new JComboBox<String>( numberOfPermutations);
		permutationPanel.add( createBorderedPanel( "Number of Permutations", createPanelWithHint(numberOfPerCombo, Commons.GUI_HINT_NUMBER_OF_PERMUTATIONS)));
		
		//numberOfPerInEachRun added to permutationPanel
		numberOfPerInEachRun = new JTextField(30);
		numberOfPerInEachRun.setText("2000");
		permutationPanel.add( createBorderedPanel( "Number of Permutations In Each Run", createPanelWithHint(numberOfPerInEachRun, Commons.GUI_HINT_FDR)));
		
		enrichmentPanel.add( permutationPanel);
        
        //rsatOption added to enrichmentPanel
        JPanel rsatOption = new JPanel( new GridLayout(0, 1));
        
        //regulatorySequenceAnalysisUsingRSATCheck added to rsatOption
        regulatorySequenceAnalysisUsingRSATCheck = new JCheckBox( "Regulatory Sequence Analysis Using RSAT");
        rsatOption.add( regulatorySequenceAnalysisUsingRSATCheck);
        enrichmentPanel.add( createBorderedPanel( "RSAT", createPanelWithHint(rsatOption, Commons.GUI_HINT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)));
        listPane.add( createBorderedPanel( "Enrichment", enrichmentPanel));
        
        //jobName added to listPane
        jobName = new JTextField();
        listPane.add( createBorderedPanel( "Job Name", jobName));
        
        JPanel rsButtonPane = new JPanel( new FlowLayout());
        
        //runButton added to rsButtonPane
        runButton = new JButton("Run");
		runButton.addActionListener( runButtonPressed);
		rsButtonPane.add( runButton);
        
        //stopButton added to rsButtonPane
        stopButton = new JButton("Stop");
        stopButton.addActionListener( stopButtonPressed);
        stopButton.setEnabled( false);
        rsButtonPane.add( stopButton);
        
        listPane.add( rsButtonPane);
        
        //logArea added to listPane
        logArea = new JTextArea( 5, 20);
        JScrollPane logAreaScrollPane = new JScrollPane( logArea);
        logAreaScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logAreaScrollPane.setPreferredSize(new Dimension(250, 250));
        logArea.setEditable( false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        listPane.add( createBorderedPanel( "GLANET Log", logAreaScrollPane));
        
        //currentWorkLabel added to listPane
        currentWorkLabel = new JLabel("");
        listPane.add( currentWorkLabel);
        
        //scroll pane added to this view
        add( scrollPane);
        
        //all control operations are done after the gui is completely set
        enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
        checkUsabilityOfRegulatorySequenceAnalysis();
        
        revalidate();
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
	
	JPanel createBrowseFileArea( String fileType, JTextField textField, String description){
	    
	    JButton browseButton = new JButton( "Browse");
	    browseButton.addActionListener( chooseFilePressed);
	    browseButton.setActionCommand( fileType);
	    
	    JPanel browsePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    browsePanel.add( browseButton);
	    browsePanel.add( textField);
	    
	    return createBorderedPanel(fileType, (description == null)?browsePanel:createPanelWithHint(browsePanel, description));
	}
	
	JPanel createPanelWithHint( JComponent component, String description){
		
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
		JPanel componentWithHint = new JPanel( new FlowLayout(FlowLayout.LEFT));
		ImageIcon hintImage = new ImageIcon("image/hint.png");
		JLabel hintLabel = new JLabel( hintImage);
		hintLabel.setToolTipText( description);
		hintLabel.setPreferredSize( new Dimension(20,20));
		
		componentWithHint.add( component);
		componentWithHint.add( hintLabel);
		
		return componentWithHint;
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
		
		currentWorkLabel.setText( info);
		revalidate();
	}
	
	public void enableEnrichmentOptions( boolean shouldEnable){
		
		generateRandomDataModeCombo.setEnabled( shouldEnable);
		multipleTestingCombo.setEnabled( shouldEnable);
		falseDiscoveryRate.setEnabled( shouldEnable);
		signifanceCriteria.setEnabled( shouldEnable);
		numberOfPerCombo.setEnabled( shouldEnable);
		numberOfPerInEachRun.setEnabled( shouldEnable);
		
		revalidate();
	}
	
	public void checkUsabilityOfRegulatorySequenceAnalysis(){
		
		if( tfEnrichment.isSelected() || tfAndKeggPathwayEnrichment.isSelected() || cellLineBasedTfAndKeggPathwayEnrichment.isSelected()){
			if( performEnrichmentCheckBox.isSelected())
				regulatorySequenceAnalysisUsingRSATCheck.setEnabled( true);
			else
				regulatorySequenceAnalysisUsingRSATCheck.setEnabled( false);
		}
  	  	else { 
  	  		regulatorySequenceAnalysisUsingRSATCheck.setSelected( false);
  	  		regulatorySequenceAnalysisUsingRSATCheck.setEnabled( false);
  	  	}
		
		revalidate();
	}
	
	public void appendNewTextToLogArea( String text){
		
		logArea.append( text + "\n");
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	
	public void appendNewTextToLogArea( int text){
		
		logArea.append( text + "\n");
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	
	public void appendNewTextToLogArea( float text){
		
		logArea.append( text + "\n");
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	
	public void enableStartProcess( boolean enable){
		
		runButton.setEnabled( enable);
		stopButton.setEnabled( !enable);
	}
	
	//if you want to change the cell lines, please change here
	private String[] createCellLines() {
		
		String[] cellLines = { "AG04449",
				"AG04450",
				"AG09309",
				"AG09319",
				"AG10803",
				"AOAF",
				"BJ",
				"CACO2",
				"CHORION",
				"CMK",
				"D721",
				"FB0167P",
				"FB8470",
				"FIBROBLASTS_PARK",
				"GM06990",
				"GM12865",
				"GM12878",
				"GM12891",
				"GM12892",
				"GM18507",
				"GM19238",
				"GM19239",
				"GM19240",
				"H1_ES",
				"H9_ES",
				"HAEPIC",
				"HBMEC",
				"HCF",
				"HCFAA",
				"HCM",
				"HCONF",
				"HCPEPIC",
				"HCT116",
				"HEEPIC",
				"HELA",
				"HELAS3",
				"HELAS3_IFNA",
				"HEPG2",
				"HESC",
				"HESCT0",
				"HGF",
				"HL60",
				"HMEC",
				"HMF",
				"HMVEC_DBLAD",
				"HMVEC_DBLNEO",
				"HMVEC_DLYAD",
				"HMVEC_DLYNEO",
				"HMVEC_DNEO",
				"HMVEC_LBL",
				"HMVEC_LLY",
				"HNPCEPIC",
				"HPAF",
				"HPDLF",
				"HPF",
				"HRCE",
				"HRE",
				"HRPEPIC",
				"HTH1",
				"HTH2",
				"HUVEC",
				"HVMF",
				"K562",
				"LHSR",
				"LHSR_INDUCED",
				"MCF7",
				"MELANOCYTE",
				"MYOBLAST",
				"MYOMETRIAL",
				"MYOTUBE",
				"NB4",
				"NHA",
				"NHDF_AD",
				"NHDF_NEO",
				"NHEK",
				"NHLF",
				"PANC1",
				"PANCREATIC_ISLETS",
				"SAEC",
				"SKMC",
				"SKNSH",
				"SM_SFM",
				"H1HESC",
				"HSMM",
				"HSMMT",
				"NHDFAD",
				"OSTEOBL",
				"A549",
				"ECC1",
				"HTB11",
				"PFSK1",
				"SKNSHRA",
				"T47D",
				"U87",
				"FIBROBL",
				"GLIOBLA",
				"PROGFIB",
				"GM10847",
				"GM15510",
				"GM18505",
				"GM18526",
				"GM18951",
				"GM19099",
				"GM19193",
				"HEK293B",
				"HEPG2B",
				"K562B",
				"MCF10AES",
				"NT2D1",
				"PBDE",
				"RAJI",
				"SHSY5Y",
				"TREXHEK293",
				"U2OS",
				"GM12801",
				"GM12864",
				"GM12872",
				"GM12873",
				"GM12874",
				"GM12875",
				"HASP",
				"HCPE",
				"HEE",
				"HEK293",
				"HRPE",
				"WERIRB1",
				"H7ES",
				"JURKAT",
				"NHDFNEO"};
		
		return cellLines;
	}
}
