package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;              //for layout managers and more
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;

import common.Commons;

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
	private JTextField userDefinedGeneSetInput;
	private JTextField userDefinedGeneSetName;
	private JTextField userDefinedGeneSetDescriptionFile;
	private JTextField userDefinedLibraryInput;
	private JLabel currentWorkLabel;
	private JPanel listPane;
	private JButton runButton;
	private JButton stopButton;
	private JComboBox<String> generateRandomDataModeCombo;
	private JComboBox<String> multipleTestingCombo;
	private JComboBox<String> numberOfPerCombo;
	private JComboBox<String> numberOfPerInEachRun;
	private JComboBox<String> inputFormatCombo;
	private JComboBox<String> inputAssembly;
	private JComboBox<String> userDefinedGeneSetGeneInformation;
	private JComboBox<String> userDefinedLibraryDataFormatCombo;
	private JCheckBox performEnrichmentCheckBox;
	private JCheckBox regulatorySequenceAnalysisUsingRSATCheck;
	private JCheckBox dnaseAnnotation;
	private JCheckBox histoneAnnotation;
	private JCheckBox tfAndKeggPathwayAnnotation;
	private JCheckBox tfAnnotation;
	private JCheckBox keggPathwayAnnotation;
	private JCheckBox cellLineBasedTfAndKeggPathwayAnnotation;
	private JCheckBox userDefinedGeneSetAnnotation;
	private JCheckBox userDefinedLibraryAnnotation;
	private JTextArea logArea;
	private JList<String> cellLinesList;
	private DefaultListModel<String> listModel;
	
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
											   String userDefinedGeneSetEnrichment,
											   String userDefinedGeneSetInputFile,
											   String userDefinedGeneSetGeneInformation,
											   String userDefinedGeneSetName,
											   String userDefinedGeneSetDescription,
											   String userDefinedLibraryEnrichment,
											   String userDefinedLibraryInputFile,
											   String userDefinedLibraryDataFormat,
											   String givenInputDataType,
											   String[] cellLinesToBeConsidered);
		
		public void stopCurrentProcess();
	}
	
	private ActionListener chooseFilePressed = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFileChooser fc = new JFileChooser();
			int returnVal;
			
			if( e.getActionCommand() == "Glanet Folder")
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			else if( e.getActionCommand() == "Input File Name")
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			else if( e.getActionCommand() == "User Defined GeneSet Input File"){
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if( !userDefinedGeneSetAnnotation.isSelected())
					return;
			}
			else if( e.getActionCommand() == "Description File"){
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if( !userDefinedGeneSetAnnotation.isSelected())
					return;
			}
			else if( e.getActionCommand() == "User Defined Library Input File"){
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if( !userDefinedLibraryAnnotation.isSelected())
					return;
			}
			
			returnVal = fc.showOpenDialog(MainView.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            //This is where a real application would open the file.
	            if( e.getActionCommand() == "Glanet Folder") {
	            	outputTextField.setText( file.getPath() + System.getProperty("file.separator"));
	            	cellLinesList.removeAll();
	            	String[] cellLinesArray = createCellLines();
	            	
	            	listModel.clear();
	            	
	            	for( int i = 0; i < cellLinesArray.length; i++)
	            		listModel.addElement( cellLinesArray[i]);
	            }
	            else if( e.getActionCommand() == "Input File Name")
	            	inputTextField.setText( file.getPath() + System.getProperty("file.separator"));
	            else if( e.getActionCommand() == "User Defined GeneSet Input File")
	            	userDefinedGeneSetInput.setText( file.getPath() + System.getProperty("file.separator"));
	            else if( e.getActionCommand() == "Description File")
	            	userDefinedGeneSetDescriptionFile.setText( file.getPath() + System.getProperty("file.separator"));
	            else if( e.getActionCommand() == "User Defined Library Input File")
	            	userDefinedLibraryInput.setText( file.getPath() + System.getProperty("file.separator"));
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
						dnaseAnnotation.isSelected()?Commons.DO_DNASE_ENRICHMENT:Commons.DO_NOT_DNASE_ENRICHMENT,
						histoneAnnotation.isSelected()?Commons.DO_HISTONE_ENRICHMENT:Commons.DO_NOT_HISTONE_ENRICHMENT,
						tfAnnotation.isSelected()?Commons.DO_TF_ENRICHMENT:Commons.DO_NOT_TF_ENRICHMENT,
						keggPathwayAnnotation.isSelected()?Commons.DO_KEGGPATHWAY_ENRICHMENT:Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT,
						tfAndKeggPathwayAnnotation.isSelected()?Commons.DO_TF_KEGGPATHWAY_ENRICHMENT:Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT,
						cellLineBasedTfAndKeggPathwayAnnotation.isSelected()?Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT:Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,
						regulatorySequenceAnalysisUsingRSATCheck.isSelected()?Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT:Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT,
						(jobName.getText().length() == 0)?Commons.NO_NAME:jobName.getText(),
						Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA,
						Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT,
						Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT,
						numberOfPerInEachRun.getSelectedItem().toString(),
						userDefinedGeneSetAnnotation.isSelected()?Commons.DO_USER_DEFINED_GENESET_ENRICHMENT:Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT,
						userDefinedGeneSetInput.getText(),
						userDefinedGeneSetGeneInformation.getSelectedItem().toString(),
						(userDefinedGeneSetName.getText().length()!=0)?userDefinedGeneSetName.getText():Commons.NO_NAME,
						(userDefinedGeneSetDescriptionFile.getText().length()!=0)?userDefinedGeneSetDescriptionFile.getText():Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED,
						userDefinedLibraryAnnotation.isSelected()?Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT:Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT,
						userDefinedLibraryInput.getText(),
						userDefinedLibraryDataFormatCombo.getSelectedItem().toString(),
						Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS,
						cellLinesList.getSelectedValuesList().toArray( new String[0])
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
	      @Override
		public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  checkUsabilityOfEnrichmentOptions();
	    	  enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
	      }
	};
	
	ItemListener enableEnrichmentOptionsListener = new ItemListener() {
	      @Override
		public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
	      }
	};
	
	ItemListener enableRegulatorySequenceAnalysis = new ItemListener() {
	      @Override
		public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  checkUsabilityOfRegulatorySequenceAnalysis();
	      }
	};
	
	ItemListener enableUserDefinedGeneSet = new ItemListener() {
	      @Override
		public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  enableUserDefinedGeneSetOptions( userDefinedGeneSetAnnotation.isSelected());
	      }
	};
	
	ItemListener enableUserDefinedLibrary = new ItemListener() {
	      @Override
		public void itemStateChanged(ItemEvent itemEvent) {
	    	  
	    	  enableUserDefinedLibraryOptions( userDefinedLibraryAnnotation.isSelected());
	      }
	};
	
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
		String[] inputFormat = {	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
									Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
									Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE,
									Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
									Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
								};
		
		inputFormatCombo = new JComboBox<String>( inputFormat);
		inputBrowseAndOptionPane.add( createBorderedPanel( "Input Format", createPanelWithHint( inputFormatCombo, Commons.GUI_HINT_INPUT_FORMAT)));
		
		String[] assemblyFormat = { Commons.INPUT_ASSEMBLY_HG_19 };
		inputAssembly = new JComboBox<String>( assemblyFormat);
		inputBrowseAndOptionPane.add( createBorderedPanel( "Assembly", createPanelWithHint( inputAssembly, Commons.GUI_HINT_ASSEMBLY_FORMAT)));
		
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
		JPanel annotationOptions = new JPanel();
		annotationOptions.setLayout( new BoxLayout(annotationOptions, BoxLayout.PAGE_AXIS));
		
		//dnaseAnnotation added to annotationOptions
		dnaseAnnotation = new JCheckBox(Commons.GUI_HINT_CELLLINE_BASED_DNASE_ANNOTATION);
		dnaseAnnotation.addItemListener(enableEnrichmentListener);
		annotationOptions.add( createPanelWithHint(dnaseAnnotation, Commons.GUI_HINT_CELLLINE_BASED_DNASE_ANNOTATION));
		
		//histoneAnnotation added to annotationOptions
		histoneAnnotation = new JCheckBox(Commons.GUI_HINT_CELLLINE_BASED_HISTONE_ANNOTATION);
		histoneAnnotation.addItemListener(enableEnrichmentListener);
		annotationOptions.add( createPanelWithHint(histoneAnnotation, Commons.GUI_HINT_CELLLINE_BASED_HISTONE_ANNOTATION));
		
		//tfAnnotation added to annotationOptions
		tfAnnotation = new JCheckBox(Commons.GUI_HINT_CELLLINE_BASED_TF_ANNOTATION);
		tfAnnotation.addItemListener(enableEnrichmentListener);
		tfAnnotation.addItemListener( enableRegulatorySequenceAnalysis);
		annotationOptions.add( createPanelWithHint(tfAnnotation, Commons.GUI_HINT_CELLLINE_BASED_TF_ANNOTATION));
		
		//keggPathwayAnnotation added to annotationOptions
        keggPathwayAnnotation = new JCheckBox(Commons.GUI_HINT_KEGG_PATHWAY_ANNOTATION);
        keggPathwayAnnotation.addItemListener(enableEnrichmentListener);
        annotationOptions.add( createPanelWithHint(keggPathwayAnnotation, Commons.GUI_HINT_KEGG_PATHWAY_ANNOTATION));
        
        //tfAndKeggPathwayAnnotation added to annotationOptions
        tfAndKeggPathwayAnnotation = new JCheckBox(Commons.GUI_HINT_TF_AND_KEGG_PATHWAY_ANNOTATION);
        tfAndKeggPathwayAnnotation.addItemListener(enableEnrichmentListener);
        tfAndKeggPathwayAnnotation.setName( "TFAndKEGGPathwayEnrichment");
        tfAndKeggPathwayAnnotation.addItemListener( enableRegulatorySequenceAnalysis);
        annotationOptions.add( createPanelWithHint(tfAndKeggPathwayAnnotation, Commons.GUI_HINT_TF_AND_KEGG_PATHWAY_ANNOTATION));
        
        //cellLineBasedTfAndKeggPathwayAnnotation added to annotationOptions
        cellLineBasedTfAndKeggPathwayAnnotation = new JCheckBox(Commons.GUI_HINT_CELLLINE_BASED_TF_AND_KEGG_PATHWAY_ANNOTATION);
        cellLineBasedTfAndKeggPathwayAnnotation.addItemListener(enableEnrichmentListener);
        cellLineBasedTfAndKeggPathwayAnnotation.setName( "cellLineBasedTfAndKeggPathwayEnrichment");
        cellLineBasedTfAndKeggPathwayAnnotation.addItemListener( enableRegulatorySequenceAnalysis);
        annotationOptions.add( createPanelWithHint( cellLineBasedTfAndKeggPathwayAnnotation, Commons.GUI_HINT_CELLLINE_BASED_TF_AND_KEGG_PATHWAY_ANNOTATION));
        
        //userDefinedGeneSetPanel added to annotationOptions
        JPanel userDefinedGeneSetPanel = new JPanel();
        userDefinedGeneSetPanel.setLayout( new BoxLayout(userDefinedGeneSetPanel, BoxLayout.PAGE_AXIS));
        
        //userDefinedGeneSetAnnotation added to userDefinedGeneSetPanel
        userDefinedGeneSetAnnotation = new JCheckBox(Commons.GUI_HINT_USER_DEFINED_GENESET_ANNOTATION);
        userDefinedGeneSetAnnotation.addItemListener( enableEnrichmentListener);
        userDefinedGeneSetAnnotation.addItemListener( enableUserDefinedGeneSet);
        
        userDefinedGeneSetPanel.add( createPanelWithHint(userDefinedGeneSetAnnotation, Commons.GUI_HINT_USER_DEFINED_GENESET_ANNOTATION));
        
        //userDefinedGeneSetUpperPanel added to userDefinedGeneSetPanel
        JPanel userDefinedGeneSetUpperPanel = new JPanel( new GridLayout(1, 2));
        
        //userDefinedGeneSetInput added to userDefinedGeneSetUpperPanel
      	userDefinedGeneSetInput = new JTextField(30);
      	userDefinedGeneSetUpperPanel.add( createBrowseFileArea( "User Defined GeneSet Input File", userDefinedGeneSetInput, Commons.GUI_HINT_USER_DEFINED_GENESET_INPUTFILE));
      	
      	//userDefinedGeneSetGeneInformation added to userDefinedGeneSetUpperPanel
      	String[] geneInformation = { Commons.GENE_ID, Commons.GENE_SYMBOL, Commons.RNA_NUCLEOTIDE_ACCESSION};
      		
      	userDefinedGeneSetGeneInformation = new JComboBox<String>( geneInformation);
      	userDefinedGeneSetUpperPanel.add( createBorderedPanel( "Gene Information Type", createPanelWithHint( userDefinedGeneSetGeneInformation, Commons.GUI_HINT_USER_DEFINED_GENESET_GENEINFORMATIONTYPE)));
      	
      	//userDefinedGeneSetLowerPanel added to userDefinedGeneSetPanel
      	JPanel userDefinedGeneSetLowerPanel = new JPanel( new GridLayout(1, 2));
      	
      	//userDefinedGeneSetName added to userDefinedGeneSetLowerPanel
      	userDefinedGeneSetName = new JTextField(20);
      	userDefinedGeneSetLowerPanel.add(createBorderedPanel( "Name", createPanelWithHint(userDefinedGeneSetName, Commons.GUI_HINT_USER_DEFINED_GENESET_NAME)));
      	
      	//userDefinedGeneSetDescriptionFile added to userDefinedGeneSetLowerPanel
      	userDefinedGeneSetDescriptionFile = new JTextField(30);
      	userDefinedGeneSetLowerPanel.add( createBrowseFileArea( "Description File", userDefinedGeneSetDescriptionFile, Commons.GUI_HINT_USER_DEFINED_GENESET_DESCRIPTION_FILE));
		
      	userDefinedGeneSetPanel.add( userDefinedGeneSetUpperPanel);
      	userDefinedGeneSetPanel.add( userDefinedGeneSetLowerPanel);
      	
        annotationOptions.add( createBorderedPanel( "User Defined GeneSet", userDefinedGeneSetPanel));
        
        //userDefinedLibraryPanel added to annotationOptions
        JPanel userDefinedLibraryPanel = new JPanel();
        userDefinedLibraryPanel.setLayout( new BoxLayout( userDefinedLibraryPanel, BoxLayout.PAGE_AXIS));
        
        //userDefinedLibraryAnnotation added to userDefinedLibraryPanel
        userDefinedLibraryAnnotation = new JCheckBox( Commons.GUI_HINT_USER_DEFINED_LIBRARY_ANNOTATION);
        dnaseAnnotation.addItemListener(enableEnrichmentListener);
        userDefinedLibraryAnnotation.addItemListener( enableEnrichmentListener);
        userDefinedLibraryAnnotation.addItemListener( enableUserDefinedLibrary);
        
        userDefinedLibraryPanel.add( createPanelWithHint(userDefinedLibraryAnnotation, Commons.GUI_HINT_USER_DEFINED_LIBRARY_ANNOTATION));
        
        //userDefinedLibraryLowerPanel added to userDefinedLibraryPanel
        JPanel userDefinedLibraryLowerPanel = new JPanel( new GridLayout(1, 2));
        
        //userDefinedLibraryInput added to userDefinedLibraryLowerPanel
        userDefinedLibraryInput = new JTextField(30);
        userDefinedLibraryLowerPanel.add( createBrowseFileArea( "User Defined Library Input File", userDefinedLibraryInput, Commons.GUI_HINT_USER_DEFINED_LIBRARY_INPUTFILE));
        
        //inputFormatCombo added to inputBrowseAndOptionPane
      	String[] udlDataFormat = {	Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE,
      								Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE,
      								Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE,
      								Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
      								};
      		
      	userDefinedLibraryDataFormatCombo = new JComboBox<String>( udlDataFormat);
      	userDefinedLibraryLowerPanel.add( createBorderedPanel( "User Defined Library Data Format", createPanelWithHint( userDefinedLibraryDataFormatCombo, Commons.GUI_HINT_USER_DEFINED_LIBRARY_DATA_FORMAT)));
		
        userDefinedLibraryPanel.add( userDefinedLibraryLowerPanel);
        annotationOptions.add( createBorderedPanel( "User Defined Library", userDefinedLibraryPanel));
        annotationPanel.add( createBorderedPanel( "Annotation Options", annotationOptions));        
        	 
        //cellLinesScrollPane added to annotationPanel
        listModel = new DefaultListModel<>();
        cellLinesList = new JList<String>( listModel); //see the comment on the method definition (createCellLines())
        //JScrollPane cellLinesScrollPane = new JScrollPane( cellLinesList);
        cellLinesList.setVisibleRowCount( 5);
        //annotationPanel.add( createBorderedPanel( "Cell Lines To Be Considered", cellLinesScrollPane));
        
		listPane.add( createBorderedPanel( "Annotation", annotationPanel));
		
		//enrichmenPanel added to listPane
		JPanel enrichmentPanel = new JPanel();
        enrichmentPanel.setLayout( new BoxLayout(enrichmentPanel, BoxLayout.PAGE_AXIS));
        
        //enableEnrichmentCheckBox added to enrichmentPanel
        JPanel performEnrichmentPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
        performEnrichmentCheckBox = new JCheckBox( "Perform Enrichment");
        performEnrichmentCheckBox.addItemListener( enableEnrichmentOptionsListener);
        performEnrichmentCheckBox.addItemListener( enableRegulatorySequenceAnalysis);
		
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
		String[] numberOfPermutationsInEachRun = { "1000", "5000", "10000" };
		numberOfPerInEachRun = new JComboBox<String>( numberOfPermutationsInEachRun);
		permutationPanel.add( createBorderedPanel( "Number of Permutations In Each Run", createPanelWithHint(numberOfPerInEachRun, Commons.GUI_HINT_NUMBER_OF_PERMUTATIONS_IN_EACH_RUN)));
		
		enrichmentPanel.add( permutationPanel);
        
        //rsatOption added to enrichmentPanel
        JPanel rsatOption = new JPanel( new GridLayout(0, 1));
        
        //regulatorySequenceAnalysisUsingRSATCheck added to rsatOption
        regulatorySequenceAnalysisUsingRSATCheck = new JCheckBox( "Regulatory Sequence Analysis Using RSAT");
        rsatOption.add( regulatorySequenceAnalysisUsingRSATCheck);
        enrichmentPanel.add( createBorderedPanel( "RSAT", createPanelWithHint(rsatOption, Commons.GUI_HINT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)));
        listPane.add( createBorderedPanel( "Enrichment", enrichmentPanel));
                
        //jobNamePanel added to listPane
		JPanel jobNamePanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
		
		//jobName added to jobNamePanel
		jobName = new JTextField(30);
		jobNamePanel.add( createPanelWithHint( jobName, Commons.GUI_HINT_JOB_NAME));
		listPane.add( createBorderedPanel( "Job Name", jobNamePanel));
        
		//currentWorkLabel added to listPane
        JPanel workPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
        currentWorkLabel = new JLabel("The execution has not yet begun");
        workPanel.add( currentWorkLabel);
        listPane.add( createBorderedPanel( "Current Status", workPanel));
        
        JPanel rsButtonPane = new JPanel( new FlowLayout());
        
        //runButton added to rsButtonPane
        runButton = new JButton("Run");
		runButton.addActionListener( runButtonPressed);
		rsButtonPane.add( runButton);
        
        //stopButton added to rsButtonPane
        stopButton = new JButton("Stop");
        stopButton.addActionListener( stopButtonPressed);
        stopButton.setEnabled( false);
        //rsButtonPane.add( stopButton);
        
        listPane.add( rsButtonPane);
        
        //logArea added to listPane
        logArea = new JTextArea( 5, 20);
        JScrollPane logAreaScrollPane = new JScrollPane( logArea);
        logAreaScrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        logAreaScrollPane.setPreferredSize(new Dimension(250, 250));
        logArea.setEditable( false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        listPane.add( createBorderedPanel( "GLANET Log", logAreaScrollPane));
        
        //scroll pane added to this view
        add( scrollPane);
        
        //all control operations are done after the gui is completely set
        enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
        checkUsabilityOfEnrichmentOptions();
        checkUsabilityOfRegulatorySequenceAnalysis();
        enableUserDefinedGeneSetOptions( userDefinedGeneSetAnnotation.isSelected());
        enableUserDefinedLibraryOptions( userDefinedLibraryAnnotation.isSelected());
        
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
		ImageIcon hintImage = new ImageIcon( getClass().getClassLoader().getResource("hint.png"));
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
	
	public void checkUsabilityOfEnrichmentOptions() {
		
		if( dnaseAnnotation.isSelected() || histoneAnnotation.isSelected() ||
				tfAnnotation.isSelected() || keggPathwayAnnotation.isSelected() ||
				tfAndKeggPathwayAnnotation.isSelected() || cellLineBasedTfAndKeggPathwayAnnotation.isSelected() ||
				userDefinedGeneSetAnnotation.isSelected() || userDefinedLibraryAnnotation.isSelected() ) {
			
			performEnrichmentCheckBox.setEnabled( true);
		} else {
			
			performEnrichmentCheckBox.setSelected( false);
			performEnrichmentCheckBox.setEnabled( false);
		}
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
	
	public void enableUserDefinedGeneSetOptions( boolean shouldEnable){
			
			userDefinedGeneSetInput.setEnabled( shouldEnable);
			userDefinedGeneSetDescriptionFile.setEnabled( shouldEnable);
			userDefinedGeneSetGeneInformation.setEnabled( shouldEnable);
			userDefinedGeneSetName.setEnabled( shouldEnable);
			
			revalidate();
		}

	public void enableUserDefinedLibraryOptions( boolean shouldEnable){
		
		userDefinedLibraryInput.setEnabled( shouldEnable);
		userDefinedLibraryDataFormatCombo.setEnabled( shouldEnable);
		
		revalidate();
	}
	
	public void checkUsabilityOfRegulatorySequenceAnalysis(){
		
		if( tfAnnotation.isSelected() || tfAndKeggPathwayAnnotation.isSelected() || cellLineBasedTfAndKeggPathwayAnnotation.isSelected()){
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
		
		if( outputTextField == null || outputTextField.getText().length() < 1)
			return new String[0];
		
		ArrayList<String> cellLinesListFromFile = new ArrayList<String>();
		
		try {
			
			File file = new File( outputTextField.getText() + Commons.DATA + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELL_LINES_NAMES_FILENAME);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader( fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null)
				cellLinesListFromFile.add( line);
			
			fileReader.close();
		} catch (IOException e) {}
		
		return cellLinesListFromFile.toArray( new String[0]);
	}
}
