/**
 * @author burcakotlu
 * @date Sep 28, 2014 
 * @time 6:16:48 PM
 */
package userdefined;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import auxiliary.FileOperations;

/**
 * This code generates GO annotations input file for testing purposes (testing
 * data for user defined geneset facility)
 * 
 */
public class GOAnnotationsInputFileGeneration {

	public static void readGOAnnotatiosInputFileAndWriteGOAnnotationsOutputFile( String inputFileName,
			String outputFileName) {

		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;

		String GO_ID;
		String geneSymbol;
		int numberofLinesRead = 0;
		int numberofLinesWritten = 0;
		int numberofCommentLines = 0;

		try{
			FileReader fileReader = FileOperations.createFileReader( inputFileName);
			BufferedReader bufferedReader = new BufferedReader( fileReader);

			FileWriter fileWriter = FileOperations.createFileWriter( outputFileName);
			BufferedWriter bufferedWriter = new BufferedWriter( fileWriter);

			while( ( strLine = bufferedReader.readLine()) != null){

				// Skip comment lines
				if( !( strLine.startsWith( "!"))){

					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
					indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
					indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);
					indexofFifthTab = strLine.indexOf( '\t', indexofFourthTab + 1);

					geneSymbol = strLine.substring( indexofSecondTab + 1, indexofThirdTab);
					GO_ID = strLine.substring( indexofFourthTab + 1, indexofFifthTab);

					bufferedWriter.write( GO_ID + "\t" + geneSymbol + System.getProperty( "line.separator"));

					numberofLinesWritten++;
				}// End of IF
				else{
					numberofCommentLines++;
				}

				numberofLinesRead++;

			}// End of WHILE

			System.out.println( "Number of lines written: " + numberofLinesWritten);
			System.out.println( "Number of comment lines: " + numberofCommentLines);
			System.out.println( "Number of lines read: " + numberofLinesRead);

			bufferedReader.close();
			bufferedWriter.close();

		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void readGeneAssociationGOPRefHumanFileWriteGOTermGeneEvidenceCodeOntology(
			String geneAssociationGOARefHumanFile,
			String GOTermGeneSymbolEvidenceCodeOntologyFile,
			List<String> experimentalEvidenceCodeList){
		
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter =null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		
		String geneSymbol = null;
		String GOTerm = null;
		String evidenceCode = null;
		String ontology = null;
		

		try {
			fileReader = FileOperations.createFileReader(geneAssociationGOARefHumanFile);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(GOTermGeneSymbolEvidenceCodeOntologyFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while( ( strLine = bufferedReader.readLine()) != null){

				// Skip comment lines
				if( !( strLine.startsWith( "!"))){

					//example strLine
					//Please notice that there are two tabs between third column and fourth column
					//UniProtKB	A0A024R161	DNAJC25-GNG10		GO:0004871	GO_REF:0000038	IEA	UniProtKB-KW:KW-0807	F	Guanine nucleotide-binding protein subunit gamma	A0A024R161_HUMAN|DNAJC25-GNG10|hCG_1994888	protein	taxon:9606	20160507	UniProt		
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab + 1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab + 1);
					indexofFourthTab = strLine.indexOf('\t', indexofThirdTab + 1);
					indexofFifthTab = strLine.indexOf('\t', indexofFourthTab + 1);
					indexofSixthTab = strLine.indexOf('\t', indexofFifthTab + 1);
					indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab + 1);
					indexofEigthTab = strLine.indexOf('\t', indexofSeventhTab + 1);
					indexofNinethTab = strLine.indexOf('\t', indexofEigthTab + 1);

					//GeneSymbol is between theSecondTab and theThirdTab
					geneSymbol = strLine.substring( indexofSecondTab + 1, indexofThirdTab);
					
					//GOTerm is between the FourthTab and the FifthTab
					GOTerm = strLine.substring( indexofFourthTab + 1, indexofFifthTab);
					
					//evidenceCode is between theSixthTab and theSeventhTab
					evidenceCode = strLine.substring( indexofSixthTab + 1, indexofSeventhTab);
					
					//ontology is between theeEighthTab and theNinethTab
					ontology = strLine.substring( indexofEigthTab + 1, indexofNinethTab);
					
					//Write only GOTerm geneSymbol couples with Experimental Evidence Codes
					//In order to form GOTerm from geneSymbols with experimental evidence codes
					//http://geneontology.org/book/export/html/799
					if (experimentalEvidenceCodeList.contains(evidenceCode)){
						bufferedWriter.write(GOTerm + "\t" + geneSymbol + "\t" + evidenceCode + "\t" + ontology + System.getProperty( "line.separator"));
					}//End of IF experimental evidence code
					
					
				}//End of IF not a header line
			
			}//End of WHILE
			
			//close
			bufferedReader.close();
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void fillExperimentalEvidenceCodeList(List<String> experimentalEvidenceCodeList){
		
		
//		Use of an experimental evidence code in a GO annotation indicates that 
//		the cited paper displayed results from a physical characterization of a gene or gene product that has supported the association of a GO term.
//		The Experimental Evidence codes are:

//		Inferred from Experiment (EXP)
//		Inferred from Direct Assay (IDA)
//		Inferred from Physical Interaction (IPI)
//		Inferred from Mutant Phenotype (IMP)
//		Inferred from Genetic Interaction (IGI)
//		Inferred from Expression Pattern (IEP)

		experimentalEvidenceCodeList.add("EXP");
		experimentalEvidenceCodeList.add("IDA");
		experimentalEvidenceCodeList.add("IPI");
		experimentalEvidenceCodeList.add("IMP");
		experimentalEvidenceCodeList.add("IGI");
		experimentalEvidenceCodeList.add("IEP");
		
	
	}
	
	
	public static void main( String[] args) {

//		String GOAnnotationsInputFileName = "G:\\DOKTORA_DATA\\GO\\gene_association.goa_ref_human";
//		String GOAnnotationsOutputFileName = "G:\\DOKTORA_DATA\\GO\\GO_gene_associations_human_ref.txt";
//
//		readGOAnnotatiosInputFileAndWriteGOAnnotationsOutputFile(
//				GOAnnotationsInputFileName,
//				GOAnnotationsOutputFileName);
		
		
		//11 August 2016
		String geneAssociationGOARefHumanFile = "G:\\GLANET_DATA\\GO\\gene_association.goa_ref_human_May_2016";
		String GOTermGeneSymbolEvidenceCodeOntologyFile = "G:\\GLANET_DATA\\GO\\GOTerm_GeneSymbol_EvidenceCode_Ontology.txt";
		
		List<String> experimentalEvidenceCodeList = new ArrayList<String>();
		fillExperimentalEvidenceCodeList(experimentalEvidenceCodeList);
		
		
		//gene_association.goa_ref_human.gz
		readGeneAssociationGOPRefHumanFileWriteGOTermGeneEvidenceCodeOntology(
				geneAssociationGOARefHumanFile,
				GOTermGeneSymbolEvidenceCodeOntologyFile,
				experimentalEvidenceCodeList);
		
		//After preparation copy the output files under 
		//C:\Users\Burçak\Google Drive\Data\demo_input_data\UserDefinedGeneSet\GO

	}

}
