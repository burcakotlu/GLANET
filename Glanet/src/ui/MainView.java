package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension; //for layout managers and more
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import common.Commons;
import enumtypes.CommandLineArguments;

public class MainView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;
	private MainViewDelegate delegate;
	private JTextField jobNameTextField;
	private JTextField numOfThreadsTextField;
	private JTextField glanetFolderTextField;
	private JTextField outputFolderTextField;
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
	private JComboBox<String> associationMeasureTypeCombo;
	private JComboBox<String> generateRandomDataModeCombo;
	private JComboBox<String> multipleTestingCombo;
	private JComboBox<String> numberOfPerCombo;
	private JComboBox<String> numberOfPerInEachRunCombo;
	private JComboBox<String> inputFormatCombo;
	private JComboBox<String> inputAssemblyCombo;
	private JComboBox<String> userDefinedGeneSetGeneInformationCombo;
	private JComboBox<String> userDefinedLibraryDataFormatCombo;
	private JComboBox<String> writeAnnotationFoundOverlapModeCombo;
	private JCheckBox performEnrichmentCheckBox;
	private JCheckBox performEnrichmentWithZScoresCheckBox;
	private JCheckBox regulatorySequenceAnalysisUsingRSATCheck;
	private JCheckBox dnaseAnnotation;
	private JCheckBox histoneAnnotation;
	private JCheckBox tfAndKeggPathwayAnnotation;
	private JCheckBox tfAnnotation;
	private JCheckBox geneAnnotation;
	private JCheckBox keggPathwayAnnotation;
	private JCheckBox cellLineBasedTfAndKeggPathwayAnnotation;
	private JCheckBox userDefinedGeneSetAnnotation;
	private JCheckBox userDefinedLibraryAnnotation;
	private JTextArea logArea;
	private JList<String> cellLinesList;
	private DefaultListModel<String> listModel;

	public interface MainViewDelegate {

		public void startRunActionsWithOptions( 
				String inputFileName, 
				String inputFileAssembly, 
				String glanetFolder,
				String outputFolder,
				String inputFileFormat, 				
				String associationMeasureType,
				String numberOfBases, 
				String enrichmentEnabled,
				String enrichmentWithKeepingNumberofOverlapsorNot, 
				String generateRandomDataMode,
				String multipleTestingChoice, 
				String bonferoniCorrectionSignificanceLevel, 
				String falseDiscoveryRate,
				String numberOfPermutations, 
				String dnaseAnnotationType, 
				String histoneAnnotationType,
				String tfAnnotationType, 
				String geneAnnotationType, 
				String keggPathwayAnnotationType,
				String tfAndKeggPathwayAnnotationType, 
				String cellLineBasedTfAndKeggPathwayAnnotationType,
				String regulatorySequenceAnalysisUsingRSAT, 
				String jobName,
				String writeAnnotationFoundOverlapsMode, 
				String writeAnnotationBinaryMatrixMode,
				String writeGeneratedRandomDataMode,
				String writePermutationBasedandParametricBasedAnnotationResultMode,
				String writePermutationBasedAnnotationResultMode, 
				String numberOfPermutationsInEachRun,
				String userDefinedGeneSetAnnotationType, 
				String userDefinedGeneSetInputFile,
				String userDefinedGeneSetGeneInformation, 
				String userDefinedGeneSetName,
				String userDefinedGeneSetDescription, 
				String userDefinedLibraryAnnotationType,
				String userDefinedLibraryInputFile, 
				String userDefinedLibraryDataFormat, 
				String givenInputDataType,
				String glanetRunType,
				String isochoreFamilyMode,
				String numOfThreads,
				String[] cellLinesToBeConsidered);

		public void stopCurrentProcess();
	}

	private ActionListener chooseFilePressed = new ActionListener() {

		@Override
		public void actionPerformed( ActionEvent e) {

			JFileChooser fc = new JFileChooser();
			int returnVal;

			if( e.getActionCommand() == "Glanet Folder")
				fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
			else if( e.getActionCommand() == "Output Folder")
				fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
			else if( e.getActionCommand() == "Input File Name")
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY);
			else if( e.getActionCommand() == "User Defined GeneSet Input File"){
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY);
				if( !userDefinedGeneSetAnnotation.isSelected())
					return;
			}else if( e.getActionCommand() == "Description File"){
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY);
				if( !userDefinedGeneSetAnnotation.isSelected())
					return;
			}else if( e.getActionCommand() == "User Defined Library Input File"){
				fc.setFileSelectionMode( JFileChooser.FILES_ONLY);
				if( !userDefinedLibraryAnnotation.isSelected())
					return;
			}

			returnVal = fc.showOpenDialog( MainView.this);

			if( returnVal == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				if( e.getActionCommand() == "Glanet Folder"){
					glanetFolderTextField.setText( file.getPath() + System.getProperty( "file.separator"));
					cellLinesList.removeAll();
					String[] cellLinesArray = createCellLines();

					listModel.clear();

					for( int i = 0; i < cellLinesArray.length; i++)
						listModel.addElement( cellLinesArray[i]);
				}
				else if( e.getActionCommand() == "Output Folder")
					outputFolderTextField.setText( file.getPath() + System.getProperty( "file.separator"));
				else if( e.getActionCommand() == "Input File Name")
					inputTextField.setText( file.getPath() + System.getProperty( "file.separator"));
				else if( e.getActionCommand() == "User Defined GeneSet Input File")
					userDefinedGeneSetInput.setText( file.getPath() + System.getProperty( "file.separator"));
				else if( e.getActionCommand() == "Description File")
					userDefinedGeneSetDescriptionFile.setText( file.getPath() + System.getProperty( "file.separator"));
				else if( e.getActionCommand() == "User Defined Library Input File")
					userDefinedLibraryInput.setText( file.getPath() + System.getProperty( "file.separator"));
			}
		}
	};

	private ActionListener runButtonPressed = new ActionListener() {

		@Override
		public void actionPerformed( ActionEvent e) {

			if( inputTextField.getText().length() <= 0 || glanetFolderTextField.getText().length() <= 0 || ( userDefinedGeneSetAnnotation.isSelected() && userDefinedGeneSetInput.getText().length() <= 0) || ( userDefinedLibraryAnnotation.isSelected() && userDefinedLibraryInput.getText().length() <= 0)
					|| (associationMeasureTypeCombo.getSelectedItem().toString().equals(Commons.EXISTENCE_OF_OVERLAP) && Integer.parseInt(numberOfBases.getText())<1) ){

				String dialogMessage = "Please fill all the necessary parameters:\n";

				if( inputTextField.getText().length() <= 0)
					dialogMessage += "Input File Name\n";
				if( glanetFolderTextField.getText().length() <= 0)
					dialogMessage += "GLANET Folder\n";
				if (associationMeasureTypeCombo.getSelectedItem().toString().equals(Commons.EXISTENCE_OF_OVERLAP) && Integer.parseInt(numberOfBases.getText())<1)
					dialogMessage += "Overlap definition must be at least 1 base or more\n";
				if( userDefinedGeneSetAnnotation.isSelected() && userDefinedGeneSetInput.getText().length() <= 0)
					dialogMessage += "User Defined GeneSet Input File\n";
				if( userDefinedLibraryAnnotation.isSelected() && userDefinedLibraryInput.getText().length() <= 0)
					dialogMessage += "User Defined Library Input File\n";

				JOptionPane.showMessageDialog( null, dialogMessage);
			}else{

				listPane.setEnabled( false);
				logArea.setText( "");
				logArea.setCaretPosition( logArea.getDocument().getLength());
				
				String outputFolder;
				//No outputFolder is set by user
				if( outputFolderTextField.getText().length() < 1){
					outputFolderTextField.setText(glanetFolderTextField.getText() + Commons.OUTPUT + System.getProperty( "file.separator"));
				}
				//OutputFolder is set by user but there is no file separator at the end
				else if( outputFolderTextField.getText().charAt(outputFolderTextField.getText().length()-1) != System.getProperty( "file.separator").toCharArray()[0]) {
					outputFolderTextField.setText(outputFolderTextField.getText() + System.getProperty( "file.separator"));
				}
				
				//Update outputFolder with jobName whether jobName is set by user or not.
				outputFolder = outputFolderTextField.getText() + ((jobNameTextField.getText().length() == 0)?CommandLineArguments.JobName.defaultValue():jobNameTextField.getText()) + System.getProperty( "file.separator");
				
				delegate.startRunActionsWithOptions(
						inputTextField.getText(),
						inputAssemblyCombo.getSelectedItem().toString(),
						glanetFolderTextField.getText(),
						outputFolder,
						inputFormatCombo.getSelectedItem().toString(),
						associationMeasureTypeCombo.getSelectedItem().toString(),
						(numberOfBases.getText().length() < 1)?CommandLineArguments.NumberOfBasesRequiredForOverlap.defaultValue():numberOfBases.getText(),
						performEnrichmentCheckBox.isSelected()?Commons.DO_ENRICH:Commons.DO_NOT_ENRICH,
						performEnrichmentWithZScoresCheckBox.isSelected()?Commons.PERFORM_ENRICHMENT_WITH_ZSCORE:Commons.PERFORM_ENRICHMENT_WITHOUT_ZSCORE,
						generateRandomDataModeCombo.getSelectedItem().toString(),
						multipleTestingCombo.getSelectedItem().toString(),
						(signifanceCriteria.getText().length() < 1)?CommandLineArguments.BonferroniCorrectionSignificanceCriteria.defaultValue():signifanceCriteria.getText(),
						(falseDiscoveryRate.getText().length() < 1)?CommandLineArguments.FalseDiscoveryRate.defaultValue():falseDiscoveryRate.getText(),
						numberOfPerCombo.getSelectedItem().toString(),
						dnaseAnnotation.isSelected()?Commons.DO_DNASE_ANNOTATION:Commons.DO_NOT_DNASE_ANNOTATION,
						histoneAnnotation.isSelected()?Commons.DO_HISTONE_ANNOTATION:Commons.DO_NOT_HISTONE_ANNOTATION,
						tfAnnotation.isSelected()?Commons.DO_TF_ANNOTATION:Commons.DO_NOT_TF_ANNOTATION,
						geneAnnotation.isSelected()?Commons.DO_GENE_ANNOTATION:Commons.DO_NOT_GENE_ANNOTATION,
						keggPathwayAnnotation.isSelected()?Commons.DO_KEGGPATHWAY_ANNOTATION:Commons.DO_NOT_KEGGPATHWAY_ANNOTATION,
						tfAndKeggPathwayAnnotation.isSelected()?Commons.DO_TF_KEGGPATHWAY_ANNOTATION:Commons.DO_NOT_TF_KEGGPATHWAY_ANNOTATION,
						cellLineBasedTfAndKeggPathwayAnnotation.isSelected()?Commons.DO_TF_CELLLINE_KEGGPATHWAY_ANNOTATION:Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ANNOTATION,
						regulatorySequenceAnalysisUsingRSATCheck.isSelected()?Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT:Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT,
						(jobNameTextField.getText().length() == 0)?CommandLineArguments.JobName.defaultValue():jobNameTextField.getText(),
						writeAnnotationFoundOverlapModeCombo.getSelectedItem().toString(),
						Commons.DO_NOT_WRITE_ANNOTATION_BINARY_MATRIX,
						Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA,
						Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT,
						Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT,
						numberOfPerInEachRunCombo.getSelectedItem().toString(),
						userDefinedGeneSetAnnotation.isSelected()?Commons.DO_USER_DEFINED_GENESET_ANNOTATION:Commons.DO_NOT_USER_DEFINED_GENESET_ANNOTATION,
						userDefinedGeneSetInput.getText(),
						userDefinedGeneSetGeneInformationCombo.getSelectedItem().toString(),
						(userDefinedGeneSetName.getText().length() != 0)?userDefinedGeneSetName.getText():Commons.NO_NAME,
						(userDefinedGeneSetDescriptionFile.getText().length() != 0)?userDefinedGeneSetDescriptionFile.getText():Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED,
						userDefinedLibraryAnnotation.isSelected()?Commons.DO_USER_DEFINED_LIBRARY_ANNOTATION:Commons.DO_NOT_USER_DEFINED_LIBRARY_ANNOTATION,
						userDefinedLibraryInput.getText(),
						userDefinedLibraryDataFormatCombo.getSelectedItem().toString(),
						Commons.GIVEN_INPUT_DATA_CONSISTS_OF_SNPS,
						Commons.GLANET_NORMAL_RUN,
						Commons.DO_NOT_USE_ISOCHORE_FAMILY,
						(numOfThreadsTextField.getText().length() < 1)?CommandLineArguments.NumberOfThreads.defaultValue():numOfThreadsTextField.getText(),
						cellLinesList.getSelectedValuesList().toArray( new String[0]));

				enableStartProcess( false);
			}
		}
	};

	private ActionListener stopButtonPressed = new ActionListener() {

		@Override
		public void actionPerformed( ActionEvent e) {

			delegate.stopCurrentProcess();

			stopButton.setEnabled( false);
			runButton.setEnabled( true);
		}
	};

	ItemListener enableEnrichmentListener = new ItemListener() {

		@Override
		public void itemStateChanged( ItemEvent itemEvent) {

			checkUsabilityOfEnrichmentOptions();
			enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
		}
	};
	
	ItemListener associationMeasureTypeChanged = new ItemListener() {

		@Override
		public void itemStateChanged( ItemEvent itemEvent) {

			checkUsabilityOfNumberOfBases( associationMeasureTypeCombo.getSelectedItem().toString() == Commons.EXISTENCE_OF_OVERLAP);
		}
	};

	ItemListener enableEnrichmentOptionsListener = new ItemListener() {

		@Override
		public void itemStateChanged( ItemEvent itemEvent) {

			enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
		}
	};

	ItemListener enableRegulatorySequenceAnalysis = new ItemListener() {

		@Override
		public void itemStateChanged( ItemEvent itemEvent) {

			checkUsabilityOfRegulatorySequenceAnalysis();
		}
	};

	ItemListener enableUserDefinedGeneSet = new ItemListener() {

		@Override
		public void itemStateChanged( ItemEvent itemEvent) {

			enableUserDefinedGeneSetOptions( userDefinedGeneSetAnnotation.isSelected());
		}
	};

	ItemListener enableUserDefinedLibrary = new ItemListener() {

		@Override
		public void itemStateChanged( ItemEvent itemEvent) {

			enableUserDefinedLibraryOptions( userDefinedLibraryAnnotation.isSelected());
		}
	};

	ActionListener enableInputAssemblyListener = new ActionListener() {

		@Override
		public void actionPerformed( ActionEvent e) {

			enableInputAssembly();
		}
	};

	DocumentListener inputTextFieldListener = new DocumentListener() {

		@Override
		public void removeUpdate( DocumentEvent e) {

			findGlanetFolder();
		}

		@Override
		public void insertUpdate( DocumentEvent e) {

			findGlanetFolder();
		}

		@Override
		public void changedUpdate( DocumentEvent e) {

			// do nothing
		}
	};
	
	DocumentListener glanetFolderTextFieldListener = new DocumentListener() {

		@Override
		public void removeUpdate( DocumentEvent e) {

			glanetFolderChanged();
		}

		@Override
		public void insertUpdate( DocumentEvent e) {

			glanetFolderChanged();
		}

		@Override
		public void changedUpdate( DocumentEvent e) {

			// do nothing
		}
	};
	
	public void setPreferredSizeForScrollPane(int width, int height){
		
		if( scrollPane != null){
			
			scrollPane.setPreferredSize( new Dimension( width-30, height-50));
			revalidate();
		}
	}

	public MainView() {

		// code flow goes respectively with the ui design (top to bottom)
		// you can see the hierarchy by moving down

		// holds the general content of the ui. listPane added to scrollPane
		listPane = new JPanel();
		listPane.setLayout( new BoxLayout( listPane, BoxLayout.PAGE_AXIS));

		// to enable scroll down on listPane. scrollPane added to this view
		scrollPane = new JScrollPane( listPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize( new Dimension( 1024, 768));

		// inputBrowseAndOptionPane added to listPane
		JPanel inputBrowseAndOptionPane = new JPanel();
		inputBrowseAndOptionPane.setLayout( new BoxLayout( inputBrowseAndOptionPane, BoxLayout.LINE_AXIS));
		// inputTextField added to inputBrowseAndOptionPane
		inputTextField = new JTextField( 30);
		inputBrowseAndOptionPane.add( createBrowseFileArea( "Input File Name", inputTextField, Commons.GUI_HINT_INPUT_FILE_NAME));
		inputTextField.getDocument().addDocumentListener( inputTextFieldListener);

		// inputFormatCombo added to inputBrowseAndOptionPane
		String[] inputFormat = {Commons.INPUT_FILE_FORMAT_1BASED_START_ENDINCLUSIVE_COORDINATES,
				Commons.INPUT_FILE_FORMAT_0BASED_START_ENDINCLUSIVE_COORDINATES,
				Commons.INPUT_FILE_FORMAT_BED_0BASED_START_ENDEXCLUSIVE_COORDINATES,
				Commons.INPUT_FILE_FORMAT_GFF3_1BASED_START_ENDINCLUSIVE_COORDINATES,
				Commons.INPUT_FILE_FORMAT_DBSNP_IDS};

		inputFormatCombo = new JComboBox<String>( inputFormat);
		inputFormatCombo.setSelectedIndex( 2);

		inputFormatCombo.addActionListener( enableInputAssemblyListener);

		inputBrowseAndOptionPane.add( createBorderedPanel( "Input Format",
				createPanelWithHint( inputFormatCombo, Commons.GUI_HINT_INPUT_FORMAT)));

		String[] assemblyFormat = {Commons.GRCH37_P13, Commons.GRCH38};
		inputAssemblyCombo = new JComboBox<String>( assemblyFormat);
		inputBrowseAndOptionPane.add( createBorderedPanel( "Assembly",
				createPanelWithHint( inputAssemblyCombo, Commons.GUI_HINT_ASSEMBLY_FORMAT)));

		listPane.add( inputBrowseAndOptionPane);

		// outputTextField added to listPane
		JPanel glanetAndOutputFolderPane = new JPanel();
		glanetAndOutputFolderPane.setLayout( new BoxLayout( glanetAndOutputFolderPane, BoxLayout.LINE_AXIS));
		
		glanetFolderTextField = new JTextField( 30);
		outputFolderTextField = new JTextField( 30);
		glanetAndOutputFolderPane.add( createBrowseFileArea( "Glanet Folder", glanetFolderTextField, Commons.GUI_HINT_GLANET_FOLDER));
		glanetAndOutputFolderPane.add( createBrowseFileArea( "Output Folder", outputFolderTextField, Commons.GUI_HINT_OUTPUT_FOLDER));
		
		listPane.add( glanetAndOutputFolderPane);

		// annotationPanel added to listPane
		JPanel annotationPanel = new JPanel();
		annotationPanel.setLayout( new BoxLayout( annotationPanel, BoxLayout.PAGE_AXIS));

		JPanel assocAndOverlapDefPanel = new JPanel( new GridLayout( 1, 2));
		
		String[] associationMeasureTypes = {Commons.NUMBER_OF_OVERLAPPING_BASES, Commons.EXISTENCE_OF_OVERLAP};
		associationMeasureTypeCombo = new JComboBox<String>( associationMeasureTypes);
		associationMeasureTypeCombo.addItemListener( associationMeasureTypeChanged);
		assocAndOverlapDefPanel.add( createBorderedPanel( "Association Measure Type", createPanelWithHint( associationMeasureTypeCombo, Commons.GUI_HINT_ASSOCIATION_MEASURE_TYPE)));
		
		// numberOfBasesPanel added to annotationPanel
		JPanel numberOfBasesPanel = new JPanel( new FlowLayout( FlowLayout.LEFT));
		// numberOfBasesLabel added to numberOfBasesPanel
		JLabel numberOfBasesLabel = new JLabel( "Number of Bases");
		numberOfBasesPanel.add( numberOfBasesLabel);

		// numberOfBases added to numberOfBasesPanel
		numberOfBases = new JTextField( 30);
		numberOfBases.setText( CommandLineArguments.NumberOfBasesRequiredForOverlap.defaultValue());
		numberOfBasesPanel.add( createPanelWithHint( numberOfBases, Commons.GUI_HINT_NUMBER_OF_BASES));
		assocAndOverlapDefPanel.add( createBorderedPanel( "Overlap Definition", numberOfBasesPanel));
		
		annotationPanel.add( assocAndOverlapDefPanel);
		
		// annotationOptions added to annotationPanel
		JPanel annotationOptions = new JPanel();
		annotationOptions.setLayout( new BoxLayout( annotationOptions, BoxLayout.PAGE_AXIS));

		// dnaseAnnotation added to annotationOptions
		dnaseAnnotation = new JCheckBox( Commons.GUI_HINT_CELLLINE_BASED_DNASE_ANNOTATION);
		dnaseAnnotation.addItemListener( enableEnrichmentListener);
		annotationOptions.add( createPanelWithHint( dnaseAnnotation, Commons.GUI_HINT_CELLLINE_BASED_DNASE_ANNOTATION));

		// histoneAnnotation added to annotationOptions
		histoneAnnotation = new JCheckBox( Commons.GUI_HINT_CELLLINE_BASED_HISTONE_ANNOTATION);
		histoneAnnotation.addItemListener( enableEnrichmentListener);
		annotationOptions.add( createPanelWithHint( histoneAnnotation,Commons.GUI_HINT_CELLLINE_BASED_HISTONE_ANNOTATION));

		
		// tfAnnotation added to annotationOptions
		tfAnnotation = new JCheckBox( Commons.GUI_HINT_CELLLINE_BASED_TF_ANNOTATION);
		tfAnnotation.addItemListener( enableEnrichmentListener);
		tfAnnotation.addItemListener( enableRegulatorySequenceAnalysis);
		annotationOptions.add( createPanelWithHint( tfAnnotation, Commons.GUI_HINT_CELLLINE_BASED_TF_ANNOTATION));
		
		// geneAnnotation added to annotationOptions
		geneAnnotation = new JCheckBox( Commons.GUI_HINT_GENE_ANNOTATION);
		geneAnnotation.addItemListener( enableEnrichmentListener);
		annotationOptions.add( createPanelWithHint( geneAnnotation, Commons.GUI_HINT_GENE_ANNOTATION));


		// keggPathwayAnnotation added to annotationOptions
		keggPathwayAnnotation = new JCheckBox( Commons.GUI_HINT_KEGG_PATHWAY_ANNOTATION);
		keggPathwayAnnotation.addItemListener( enableEnrichmentListener);
		annotationOptions.add( createPanelWithHint( keggPathwayAnnotation, Commons.GUI_HINT_KEGG_PATHWAY_ANNOTATION));

		// tfAndKeggPathwayAnnotation added to annotationOptions
		tfAndKeggPathwayAnnotation = new JCheckBox( Commons.GUI_HINT_TF_AND_KEGG_PATHWAY_ANNOTATION);
		tfAndKeggPathwayAnnotation.addItemListener( enableEnrichmentListener);
		tfAndKeggPathwayAnnotation.setName( "TFAndKEGGPathwayEnrichment");
		tfAndKeggPathwayAnnotation.addItemListener( enableRegulatorySequenceAnalysis);
		annotationOptions.add( createPanelWithHint( tfAndKeggPathwayAnnotation,
				Commons.GUI_HINT_TF_AND_KEGG_PATHWAY_ANNOTATION));

		// cellLineBasedTfAndKeggPathwayAnnotation added to annotationOptions
		cellLineBasedTfAndKeggPathwayAnnotation = new JCheckBox(
				Commons.GUI_HINT_CELLLINE_BASED_TF_AND_KEGG_PATHWAY_ANNOTATION);
		cellLineBasedTfAndKeggPathwayAnnotation.addItemListener( enableEnrichmentListener);
		cellLineBasedTfAndKeggPathwayAnnotation.setName( "cellLineBasedTfAndKeggPathwayEnrichment");
		cellLineBasedTfAndKeggPathwayAnnotation.addItemListener( enableRegulatorySequenceAnalysis);
		annotationOptions.add( createPanelWithHint( cellLineBasedTfAndKeggPathwayAnnotation,
				Commons.GUI_HINT_CELLLINE_BASED_TF_AND_KEGG_PATHWAY_ANNOTATION));

		// userDefinedGeneSetPanel added to annotationOptions
		JPanel userDefinedGeneSetPanel = new JPanel();
		userDefinedGeneSetPanel.setLayout( new BoxLayout( userDefinedGeneSetPanel, BoxLayout.PAGE_AXIS));

		// userDefinedGeneSetAnnotation added to userDefinedGeneSetPanel
		userDefinedGeneSetAnnotation = new JCheckBox( Commons.GUI_HINT_USER_DEFINED_GENESET_ANNOTATION);
		userDefinedGeneSetAnnotation.addItemListener( enableEnrichmentListener);
		userDefinedGeneSetAnnotation.addItemListener( enableUserDefinedGeneSet);

		userDefinedGeneSetPanel.add( createPanelWithHint( userDefinedGeneSetAnnotation,
				Commons.GUI_HINT_USER_DEFINED_GENESET_ANNOTATION));

		// userDefinedGeneSetUpperPanel added to userDefinedGeneSetPanel
		JPanel userDefinedGeneSetUpperPanel = new JPanel( new GridLayout( 1, 2));

		// userDefinedGeneSetInput added to userDefinedGeneSetUpperPanel
		userDefinedGeneSetInput = new JTextField( 30);
		userDefinedGeneSetUpperPanel.add( createBrowseFileArea( "User Defined GeneSet Input File",
				userDefinedGeneSetInput, Commons.GUI_HINT_USER_DEFINED_GENESET_INPUTFILE));

		// userDefinedGeneSetGeneInformation added to
		// userDefinedGeneSetUpperPanel
		String[] geneInformation = {Commons.GENE_ID, Commons.GENE_SYMBOL, Commons.RNA_NUCLEOTIDE_ACCESSION};

		userDefinedGeneSetGeneInformationCombo = new JComboBox<String>( geneInformation);
		userDefinedGeneSetGeneInformationCombo.setSelectedIndex( 1);
		userDefinedGeneSetUpperPanel.add( createBorderedPanel(
				"Gene Information Type",
				createPanelWithHint( userDefinedGeneSetGeneInformationCombo,
						Commons.GUI_HINT_USER_DEFINED_GENESET_GENEINFORMATIONTYPE)));

		// userDefinedGeneSetLowerPanel added to userDefinedGeneSetPanel
		JPanel userDefinedGeneSetLowerPanel = new JPanel( new GridLayout( 1, 2));

		// userDefinedGeneSetName added to userDefinedGeneSetLowerPanel
		userDefinedGeneSetName = new JTextField( 20);
		userDefinedGeneSetLowerPanel.add( createBorderedPanel( "Name",
				createPanelWithHint( userDefinedGeneSetName, Commons.GUI_HINT_USER_DEFINED_GENESET_NAME)));

		// userDefinedGeneSetDescriptionFile added to
		// userDefinedGeneSetLowerPanel
		userDefinedGeneSetDescriptionFile = new JTextField( 30);
		userDefinedGeneSetLowerPanel.add( createBrowseFileArea( "Description File", userDefinedGeneSetDescriptionFile,
				Commons.GUI_HINT_USER_DEFINED_GENESET_DESCRIPTION_FILE));

		userDefinedGeneSetPanel.add( userDefinedGeneSetUpperPanel);
		userDefinedGeneSetPanel.add( userDefinedGeneSetLowerPanel);

		annotationOptions.add( createBorderedPanel( "User Defined GeneSet", userDefinedGeneSetPanel));

		// userDefinedLibraryPanel added to annotationOptions
		JPanel userDefinedLibraryPanel = new JPanel();
		userDefinedLibraryPanel.setLayout( new BoxLayout( userDefinedLibraryPanel, BoxLayout.PAGE_AXIS));

		// userDefinedLibraryAnnotation added to userDefinedLibraryPanel
		userDefinedLibraryAnnotation = new JCheckBox( Commons.GUI_HINT_USER_DEFINED_LIBRARY_ANNOTATION);
		dnaseAnnotation.addItemListener( enableEnrichmentListener);
		userDefinedLibraryAnnotation.addItemListener( enableEnrichmentListener);
		userDefinedLibraryAnnotation.addItemListener( enableUserDefinedLibrary);

		userDefinedLibraryPanel.add( createPanelWithHint( userDefinedLibraryAnnotation,
				Commons.GUI_HINT_USER_DEFINED_LIBRARY_ANNOTATION));

		// userDefinedLibraryLowerPanel added to userDefinedLibraryPanel
		JPanel userDefinedLibraryLowerPanel = new JPanel( new GridLayout( 1, 2));

		// userDefinedLibraryInput added to userDefinedLibraryLowerPanel
		userDefinedLibraryInput = new JTextField( 30);
		userDefinedLibraryLowerPanel.add( createBrowseFileArea( "User Defined Library Input File",
				userDefinedLibraryInput, Commons.GUI_HINT_USER_DEFINED_LIBRARY_INPUTFILE));

		// inputFormatCombo added to inputBrowseAndOptionPane
		String[] udlDataFormat = {Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDEXCLUSIVE_COORDINATES,
				Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_START_ENDINCLUSIVE_COORDINATES,
				Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDEXCLUSIVE_COORDINATES,
				Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_START_ENDINCLUSIVE_COORDINATES};

		userDefinedLibraryDataFormatCombo = new JComboBox<String>( udlDataFormat);
		userDefinedLibraryLowerPanel.add( createBorderedPanel(
				"User Defined Library Data Format",
				createPanelWithHint( userDefinedLibraryDataFormatCombo,
						Commons.GUI_HINT_USER_DEFINED_LIBRARY_DATA_FORMAT)));

		userDefinedLibraryPanel.add( userDefinedLibraryLowerPanel);
		annotationOptions.add( createBorderedPanel( "User Defined Library", userDefinedLibraryPanel));
		annotationPanel.add( createBorderedPanel( "Annotation Options", annotationOptions));
		
		String[] writeAnnotationFoundOverlapsFoundModes = {Commons.DO_WRITE_FOUND_OVERLAPS_ELEMENT_BASED,
				Commons.DO_WRITE_FOUND_OVERLAPS_ELEMENT_TYPE_BASED,
				Commons.DO_NOT_WRITE_FOUND_OVERLAPS_AT_ALL};
		writeAnnotationFoundOverlapModeCombo = new JComboBox<String>(writeAnnotationFoundOverlapsFoundModes);
		
		annotationPanel.add( createBorderedPanel( "Annotation Options", createPanelWithHint( writeAnnotationFoundOverlapModeCombo, Commons.GUI_HINT_ANNOTATION_OUTPUT_OPTION)));
		
		// cellLinesScrollPane added to annotationPanel
		listModel = new DefaultListModel<>();
		cellLinesList = new JList<String>( listModel); // see the comment on the
														// method definition
														// (createCellLines())
		// JScrollPane cellLinesScrollPane = new JScrollPane( cellLinesList);
		cellLinesList.setVisibleRowCount( 5);
		// annotationPanel.add( createBorderedPanel(
		// "Cell Lines To Be Considered", cellLinesScrollPane));

		listPane.add( createBorderedPanel( "Annotation", annotationPanel));

		// enrichmenPanel added to listPane
		JPanel enrichmentPanel = new JPanel();
		enrichmentPanel.setLayout( new BoxLayout( enrichmentPanel, BoxLayout.PAGE_AXIS));

		// enableEnrichmentCheckBox added to enrichmentPanel
		JPanel performEnrichmentPanel = new JPanel( new FlowLayout( FlowLayout.LEFT));
		performEnrichmentCheckBox = new JCheckBox( "Perform Enrichment");
		performEnrichmentCheckBox.addItemListener( enableEnrichmentOptionsListener);
		performEnrichmentCheckBox.addItemListener( enableRegulatorySequenceAnalysis);

		performEnrichmentPanel.add( performEnrichmentCheckBox);

		performEnrichmentWithZScoresCheckBox = new JCheckBox( "Enrichment With ZScores");
		performEnrichmentWithZScoresCheckBox.setSelected(true);
		performEnrichmentPanel.add( performEnrichmentWithZScoresCheckBox);

		enrichmentPanel.add( performEnrichmentPanel);

		// generateRandomDataModeCombo added to enrichmentPanel
		String[] generateRandomDataModeSet = {Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT,
				Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT};
		generateRandomDataModeCombo = new JComboBox<String>( generateRandomDataModeSet);
		enrichmentPanel.add( createBorderedPanel( "Generate Random Data Mode",
				createPanelWithHint( generateRandomDataModeCombo, Commons.GUI_HINT_GENERATE_RANDOM_DATA_MODE)));

		// multipleTestingCombo added to enrichmentPanel
		String[] multipleTest = {Commons.BENJAMINI_HOCHBERG_FDR, Commons.BONFERRONI_CORRECTION};
		multipleTestingCombo = new JComboBox<String>( multipleTest);
		enrichmentPanel.add( createBorderedPanel( "Multiple Testing",
				createPanelWithHint( multipleTestingCombo, Commons.GUI_HINT_MULTIPLE_TESTING)));

		// fdrAndSigCriteria added to enrichmentPanel
		JPanel fdrAndSigCriteria = new JPanel( new GridLayout( 1, 2));

		// falseDiscoveryRate added to fdrAndSigCriteria
		falseDiscoveryRate = new JTextField( 30);
		falseDiscoveryRate.setText( CommandLineArguments.FalseDiscoveryRate.defaultValue());
		fdrAndSigCriteria.add( createBorderedPanel( "False Discovery Rate",
				createPanelWithHint( falseDiscoveryRate, Commons.GUI_HINT_FDR)));

		// signifanceCriteria added to fdrAndSigCriteria
		signifanceCriteria = new JTextField( 30);
		signifanceCriteria.setText( CommandLineArguments.BonferroniCorrectionSignificanceCriteria.defaultValue());
		fdrAndSigCriteria.add( createBorderedPanel( "Bonferroni Correction Significance Criteria",
				createPanelWithHint( signifanceCriteria, Commons.GUI_HINT_BONFERONI_CORRECTION_SIGNIFICANCE_CRITERIA)));
		enrichmentPanel.add( fdrAndSigCriteria);

		// permutationPanel added to enrichmentPanel
		JPanel permutationPanel = new JPanel( new GridLayout( 1, 2));

		// numberOfPerCombo added to permutationPanel
		String[] numberOfPermutations = {"1000","5000", "10000", "50000", "100000"};
		numberOfPerCombo = new JComboBox<String>( numberOfPermutations);
		//Default number of permutations is 10000
		numberOfPerCombo.setSelectedIndex(2);
		permutationPanel.add( createBorderedPanel( "Number of Permutations",
				createPanelWithHint( numberOfPerCombo, Commons.GUI_HINT_NUMBER_OF_PERMUTATIONS)));

		// numberOfPerInEachRun added to permutationPanel
		String[] numberOfPermutationsInEachRun = {"1000", "5000", "10000"};
		numberOfPerInEachRunCombo = new JComboBox<String>( numberOfPermutationsInEachRun);
		//Default number of permutations in each run is 10000
		numberOfPerInEachRunCombo.setSelectedIndex(2);
		permutationPanel.add( createBorderedPanel( "Number of Permutations In Each Run",
				createPanelWithHint( numberOfPerInEachRunCombo, Commons.GUI_HINT_NUMBER_OF_PERMUTATIONS_IN_EACH_RUN)));

		enrichmentPanel.add( permutationPanel);

		// rsatOption added to enrichmentPanel
		JPanel rsatOption = new JPanel( new GridLayout( 0, 1));

		// regulatorySequenceAnalysisUsingRSATCheck added to rsatOption
		regulatorySequenceAnalysisUsingRSATCheck = new JCheckBox( "Regulatory Sequence Analysis Using RSAT");
		rsatOption.add( regulatorySequenceAnalysisUsingRSATCheck);
		enrichmentPanel.add( createBorderedPanel( "Regulatory Sequence Analysis",
				createPanelWithHint( rsatOption, Commons.GUI_HINT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT)));
		listPane.add( createBorderedPanel( "Enrichment", enrichmentPanel));

		// jobNamePanel added to listPane
		JPanel jobNameAndThreadPanel = new JPanel( new GridLayout( 1, 2));

		// jobName added to jobNamePanel
		jobNameTextField = new JTextField( 30);
		numOfThreadsTextField = new JTextField(30);
		numOfThreadsTextField.setText( CommandLineArguments.NumberOfThreads.defaultValue());
		JLabel numOfThreadsLabel = new JLabel("Max available processors: " + Runtime.getRuntime().availableProcessors());
		JPanel threadFieldAndLabelPanel = new JPanel( new GridLayout( 2, 1));
		threadFieldAndLabelPanel.add(numOfThreadsLabel);
		threadFieldAndLabelPanel.add(createPanelWithHint( numOfThreadsTextField, Commons.GUI_HINT_NUMBER_OF_THREADS));
		
		jobNameAndThreadPanel.add( createBorderedPanel( "Job Name", createPanelWithHint( jobNameTextField, Commons.GUI_HINT_JOB_NAME)));
		jobNameAndThreadPanel.add( createBorderedPanel( "Number of Threads", threadFieldAndLabelPanel));
		listPane.add( jobNameAndThreadPanel);

		// currentWorkLabel added to listPane
		JPanel workPanel = new JPanel( new FlowLayout( FlowLayout.LEFT));
		currentWorkLabel = new JLabel( "The execution has not yet begun");
		workPanel.add( currentWorkLabel);
		listPane.add( createBorderedPanel( "Current Status", workPanel));

		JPanel rsButtonPane = new JPanel( new FlowLayout());

		// runButton added to rsButtonPane
		runButton = new JButton( "Run");
		runButton.addActionListener( runButtonPressed);
		rsButtonPane.add( runButton);

		// stopButton added to rsButtonPane
		stopButton = new JButton( "Stop");
		stopButton.addActionListener( stopButtonPressed);
		stopButton.setEnabled( false);
		// rsButtonPane.add( stopButton);

		listPane.add( rsButtonPane);

		// logArea added to listPane
		logArea = new JTextArea( 5, 20);
		JScrollPane logAreaScrollPane = new JScrollPane( logArea);
		logAreaScrollPane.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		logAreaScrollPane.setPreferredSize( new Dimension( 250, 250));
		logArea.setEditable( false);
		logArea.setLineWrap( true);
		logArea.setWrapStyleWord( true);
		listPane.add( createBorderedPanel( "GLANET Log", logAreaScrollPane));

		// scroll pane added to this view
		add( scrollPane);

		refreshButtons();
		revalidate();
	}

	void refreshButtons() {

		enableEnrichmentOptions( performEnrichmentCheckBox.isSelected());
		checkUsabilityOfEnrichmentOptions();
		checkUsabilityOfRegulatorySequenceAnalysis();
		checkUsabilityOfNumberOfBases( associationMeasureTypeCombo.getSelectedItem().toString() == Commons.EXISTENCE_OF_OVERLAP);
		enableUserDefinedGeneSetOptions( userDefinedGeneSetAnnotation.isSelected());
		enableUserDefinedLibraryOptions( userDefinedLibraryAnnotation.isSelected());
		enableInputAssembly();
	}

	JPanel createBorderedPanel( String borderName, JComponent panel) {

		// Create some labels for the fields.
		JLabel componentLabel = new JLabel();
		componentLabel.setLabelFor( panel);

		// Lay out the text controls and the labels.
		JPanel paneControlsPane = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		paneControlsPane.setLayout( gridbag);

		JLabel[] labels = {componentLabel};
		JComponent[] components = {panel};
		addLabelTextRows( labels, components, gridbag, paneControlsPane);

		c.gridwidth = GridBagConstraints.REMAINDER; // last
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;
		paneControlsPane.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder( borderName),
				BorderFactory.createEmptyBorder( 5, 5, 5, 5)));

		// Put everything together.
		JPanel borderedPanel = new JPanel( new BorderLayout());
		borderedPanel.add( paneControlsPane, BorderLayout.PAGE_START);

		return borderedPanel;
	}

	JPanel createBrowseFileArea( String fileType, JTextField textField, String description) {

		JButton browseButton = new JButton( "Browse");
		browseButton.addActionListener( chooseFilePressed);
		browseButton.setActionCommand( fileType);

		JPanel browsePanel = new JPanel( new FlowLayout( FlowLayout.LEFT));
		browsePanel.add( browseButton);
		browsePanel.add( textField);

		return createBorderedPanel( fileType,
				( description == null)?browsePanel:createPanelWithHint( browsePanel, description));
	}

	JPanel createPanelWithHint( JComponent component, String description) {

		ToolTipManager.sharedInstance().setInitialDelay( 0);

		JPanel componentWithHint = new JPanel( new FlowLayout( FlowLayout.LEFT));
		ImageIcon hintImage = new ImageIcon( getClass().getClassLoader().getResource( "hint.png"));
		JLabel hintLabel = new JLabel( hintImage);
		hintLabel.setToolTipText( description);
		hintLabel.setPreferredSize( new Dimension( 20, 20));

		componentWithHint.add( component);
		componentWithHint.add( hintLabel);

		return componentWithHint;
	}

	private void addLabelTextRows( JComponent[] labels, JComponent[] textFields, GridBagLayout gridbag,
			Container container) {

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;

		for( int i = 0; i < numLabels; i++){
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			container.add( labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add( textFields[i], c);
		}
	}

	public void setDelegate( MainViewDelegate delegate) {

		this.delegate = delegate;
	}

	public void setCurrentProcessInfo( String info) {

		currentWorkLabel.setText( info);
		revalidate();
	}

	public void checkUsabilityOfEnrichmentOptions() {

		if( dnaseAnnotation.isSelected() || geneAnnotation.isSelected() || histoneAnnotation.isSelected() || tfAnnotation.isSelected() || keggPathwayAnnotation.isSelected() || tfAndKeggPathwayAnnotation.isSelected() || cellLineBasedTfAndKeggPathwayAnnotation.isSelected() || userDefinedGeneSetAnnotation.isSelected() || userDefinedLibraryAnnotation.isSelected()){

			performEnrichmentCheckBox.setEnabled( true);
		}else{

			performEnrichmentCheckBox.setSelected( false);
			performEnrichmentCheckBox.setEnabled( false);
		}
	}

	public void enableEnrichmentOptions( boolean shouldEnable) {

		performEnrichmentWithZScoresCheckBox.setEnabled( shouldEnable);
		generateRandomDataModeCombo.setEnabled( shouldEnable);
		multipleTestingCombo.setEnabled( shouldEnable);
		falseDiscoveryRate.setEnabled( shouldEnable);
		signifanceCriteria.setEnabled( shouldEnable);
		numberOfPerCombo.setEnabled( shouldEnable);
		numberOfPerInEachRunCombo.setEnabled( shouldEnable);

		revalidate();
	}

	public void enableUserDefinedGeneSetOptions( boolean shouldEnable) {

		userDefinedGeneSetInput.setEnabled( shouldEnable);
		userDefinedGeneSetDescriptionFile.setEnabled( shouldEnable);
		userDefinedGeneSetGeneInformationCombo.setEnabled( shouldEnable);
		userDefinedGeneSetName.setEnabled( shouldEnable);

		revalidate();
	}

	public void enableUserDefinedLibraryOptions( boolean shouldEnable) {

		userDefinedLibraryInput.setEnabled( shouldEnable);
		userDefinedLibraryDataFormatCombo.setEnabled( shouldEnable);

		revalidate();
	}

	public void enableInputAssembly() {

		inputAssemblyCombo.setEnabled( ( inputFormatCombo.getSelectedItem().toString().equalsIgnoreCase( Commons.INPUT_FILE_FORMAT_DBSNP_IDS))?false:true);

		revalidate();
	}

	public void checkUsabilityOfRegulatorySequenceAnalysis() {

		if( tfAnnotation.isSelected() || tfAndKeggPathwayAnnotation.isSelected() || cellLineBasedTfAndKeggPathwayAnnotation.isSelected()){
			regulatorySequenceAnalysisUsingRSATCheck.setEnabled( true);
		}else{
			regulatorySequenceAnalysisUsingRSATCheck.setSelected( false);
			regulatorySequenceAnalysisUsingRSATCheck.setEnabled( false);
		}

		revalidate();
	}
	
	public void checkUsabilityOfNumberOfBases( boolean isExistenceOfOverlapSelected){
		
		numberOfBases.setEnabled( isExistenceOfOverlapSelected);
	}

	public void appendNewTextToLogArea( String text) {

		logArea.append( text + System.getProperty( "line.separator"));
		logArea.setCaretPosition( logArea.getDocument().getLength());
	}

	public void appendNewTextToLogArea( int text) {

		logArea.append( text + System.getProperty( "line.separator"));
		logArea.setCaretPosition( logArea.getDocument().getLength());
	}

	public void appendNewTextToLogArea( float text) {

		logArea.append( text + System.getProperty( "line.separator"));
		logArea.setCaretPosition( logArea.getDocument().getLength());
	}

	public void enableStartProcess( boolean shouldEnable) {

		jobNameTextField.setEnabled( shouldEnable);
		glanetFolderTextField.setEnabled( shouldEnable);
		inputTextField.setEnabled( shouldEnable);
		falseDiscoveryRate.setEnabled( shouldEnable);
		signifanceCriteria.setEnabled( shouldEnable);
		numberOfBases.setEnabled( shouldEnable);
		userDefinedGeneSetInput.setEnabled( shouldEnable);
		userDefinedGeneSetName.setEnabled( shouldEnable);
		userDefinedGeneSetDescriptionFile.setEnabled( shouldEnable);
		userDefinedLibraryInput.setEnabled( shouldEnable);
		runButton.setEnabled( shouldEnable);
		stopButton.setEnabled( !shouldEnable);
		generateRandomDataModeCombo.setEnabled( shouldEnable);
		multipleTestingCombo.setEnabled( shouldEnable);
		numberOfPerCombo.setEnabled( shouldEnable);
		numberOfPerInEachRunCombo.setEnabled( shouldEnable);
		inputFormatCombo.setEnabled( shouldEnable);
		inputAssemblyCombo.setEnabled( shouldEnable);
		userDefinedGeneSetGeneInformationCombo.setEnabled( shouldEnable);
		userDefinedLibraryDataFormatCombo.setEnabled( shouldEnable);
		performEnrichmentCheckBox.setEnabled( shouldEnable);
		performEnrichmentWithZScoresCheckBox.setEnabled( shouldEnable);
		regulatorySequenceAnalysisUsingRSATCheck.setEnabled( shouldEnable);
		dnaseAnnotation.setEnabled( shouldEnable);
		geneAnnotation.setEnabled( shouldEnable);
		histoneAnnotation.setEnabled( shouldEnable);
		tfAndKeggPathwayAnnotation.setEnabled( shouldEnable);
		tfAnnotation.setEnabled( shouldEnable);
		keggPathwayAnnotation.setEnabled( shouldEnable);
		cellLineBasedTfAndKeggPathwayAnnotation.setEnabled( shouldEnable);
		userDefinedGeneSetAnnotation.setEnabled( shouldEnable);
		userDefinedLibraryAnnotation.setEnabled( shouldEnable);
	}

	// if you want to change the cell lines, please change here
	private String[] createCellLines() {

		if( glanetFolderTextField == null || glanetFolderTextField.getText().length() < 1)
			return new String[0];

		ArrayList<String> cellLinesListFromFile = new ArrayList<String>();

		try{

			File file = new File(
					glanetFolderTextField.getText() + Commons.DATA + System.getProperty( "file.separator") + Commons.ALL_POSSIBLE_NAMES_ENCODE_OUTPUT_DIRECTORYNAME + Commons.ALL_POSSIBLE_ENCODE_CELL_LINES_NAMES_FILENAME);
			FileReader fileReader = new FileReader( file);
			BufferedReader bufferedReader = new BufferedReader( fileReader);
			String line;
			while( ( line = bufferedReader.readLine()) != null)
				cellLinesListFromFile.add( line);

			fileReader.close();
		}catch( IOException e){}

		return cellLinesListFromFile.toArray( new String[0]);
	}

	public void findGlanetFolder() {

		String inputPath = inputTextField.getText();
		if( inputPath.contains( "Data" + System.getProperty( "file.separator"))){

			glanetFolderTextField.setText( inputPath.substring( 0, inputPath.indexOf( "Data" + System.getProperty( "file.separator"))));
			glanetFolderChanged();
		}
	}
	
	public void glanetFolderChanged(){
		
		if( outputFolderTextField != null && glanetFolderTextField.getText().length() > 0 && outputFolderTextField.getText().length() < 1){
			
			outputFolderTextField.setText( glanetFolderTextField.getText() + Commons.OUTPUT + System.getProperty( "file.separator"));
		}
	}
}
