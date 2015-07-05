/*
 * HumanRefSeq2Gene.java reads gene2refseq.txt
 * It filters the homo sapiens lines of gene2refseq.txt file and write it to human_gene2refseq.txt file.
 * 
 * Later on it converts the order and writes it to human_refseq2gene.txt  and human_refseq2gene2.txt file.
 * 
 * Example 	refseq gene id and 	gene id
 * 			NR_046536			100873973
 */

package ncbi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import ui.GlanetRunner;
import auxiliary.FileOperations;
import common.Commons;
import enumtypes.CommandLineArguments;

public class HumanRefSeq2Gene {

	public static void humanGene2RefSeq( String dataFolder) {

		FileReader fileReader;
		FileWriter fileWriter;

		BufferedReader bufferedReader;
		BufferedWriter bufferedWriter;

		String strLine;
		int indexofFirstTab;
		Integer taxId;
		int numberofHumanGene2RefseqLines = 0;

		try{

			fileReader = FileOperations.createFileReader( dataFolder + Commons.NCBI_GENE_TO_REF_SEQ_18_NOV_2014);
			fileWriter = FileOperations.createFileWriter(
					dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME,
					Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014);

			bufferedReader = new BufferedReader( fileReader);
			bufferedWriter = new BufferedWriter( fileWriter);

			// Skip first line
			strLine = bufferedReader.readLine();

			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				taxId = Integer.parseInt( strLine.substring( 0, indexofFirstTab));

				if( taxId.equals( Commons.HOMO_SAPIENS_TAX_ID)){
					bufferedWriter.write( strLine + System.getProperty( "line.separator"));
					numberofHumanGene2RefseqLines++;
				}

			}

			System.out.print( "Number of humangene2refseq lines " + numberofHumanGene2RefseqLines + System.getProperty( "line.separator"));

			bufferedWriter.close();
			bufferedReader.close();

		}catch( FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void humanRNANucleotideAccession2GeneID( String dataFolder) {

		FileReader fileReader;
		FileWriter fileWriter;
		FileWriter fileWriter2;

		BufferedReader bufferedReader;
		BufferedWriter bufferedWriter;
		BufferedWriter bufferedWriter2;

		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofDot;

		int geneId;
		String RNA_Nucleotide_Accession = null;
		String RNA_Nucleotide_Accession_WithVersion = null;

		int numberofHuman_RNA_Nucleotide_Accession = 0;
		int numberofHuman_RNA_Nucleotide_Accession_WithVersion = 0;

		Set<RefSeq2Gene> refSeq2GeneSet = new HashSet<RefSeq2Gene>();
		RefSeq2Gene refSeq2Gene = null;

		try{
			fileReader = FileOperations.createFileReader(
					dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME,
					Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME_18_NOV_2014);
			fileWriter = FileOperations.createFileWriter(
					dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME,
					Commons.NCBI_RNANUCLEOTIDEACCESSION_TO_GENEID_18_NOV_2014);
			fileWriter2 = FileOperations.createFileWriter(
					dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_OUTPUT_DIRECTORYNAME,
					Commons.NCBI_RNANUCLEOTIDEACCESSIONWITHVERSION_TO_GENEID_18_NOV_2014);

			bufferedReader = new BufferedReader( fileReader);
			bufferedWriter = new BufferedWriter( fileWriter);
			bufferedWriter2 = new BufferedWriter( fileWriter2);

			while( ( strLine = bufferedReader.readLine()) != null){
				// example Line from file Commons.NCBI_HUMAN_GENE_TO_REF_SEQ
				// 9606 63976 REVIEWED NM_022114.3 289547572 NP_071397.3
				// 289547573 NC_000001.10 224589800 2985741 3355184 + Reference
				// GRCh37.p10 Primary Assembly

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);

				geneId = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));

				// RNA_Nucleotide_Accession_WithVersion may be null (-)
				RNA_Nucleotide_Accession_WithVersion = strLine.substring( indexofThirdTab + 1, indexofFourthTab);

				indexofDot = RNA_Nucleotide_Accession_WithVersion.indexOf( '.');
				RNA_Nucleotide_Accession = null;
				if( indexofDot >= 0){
					RNA_Nucleotide_Accession = RNA_Nucleotide_Accession_WithVersion.substring( 0, indexofDot);
				}

				if( !( RNA_Nucleotide_Accession_WithVersion.equals( Commons.STRING_HYPHEN))){

					refSeq2Gene = new RefSeq2Gene();

					refSeq2Gene.setGeneId( geneId);

					if( RNA_Nucleotide_Accession != null){
						refSeq2Gene.setRNA_Nucleotide_Accession( RNA_Nucleotide_Accession);
					}

					refSeq2Gene.setRNA_Nucleotide_Accession_WithVersion( RNA_Nucleotide_Accession_WithVersion);

					if( !( refSeq2GeneSet.contains( refSeq2Gene))){

						refSeq2GeneSet.add( refSeq2Gene);

						if( RNA_Nucleotide_Accession != null){
							bufferedWriter.write( RNA_Nucleotide_Accession + "\t" + geneId + System.getProperty( "line.separator"));
							numberofHuman_RNA_Nucleotide_Accession++;
						}

						bufferedWriter2.write( RNA_Nucleotide_Accession_WithVersion + "\t" + geneId + System.getProperty( "line.separator"));
						numberofHuman_RNA_Nucleotide_Accession_WithVersion++;
					}else{
						refSeq2Gene = null;
					}

				}// End of IF RNA_Nucleotide_Accession_WithVersion is not null
					// (-)
			} // End of While

			GlanetRunner.appendLog( "Number of Human_RNA_Nucleotide_Accession lines " + numberofHuman_RNA_Nucleotide_Accession);
			GlanetRunner.appendLog( "Number of Human_RNA_Nucleotide_Accession_WithVersion " + numberofHuman_RNA_Nucleotide_Accession_WithVersion);

			bufferedWriter.close();
			bufferedWriter2.close();
			bufferedReader.close();
			refSeq2GeneSet = null;

		}catch( FileNotFoundException e){
			e.printStackTrace();
		}catch( IOException e){
			e.printStackTrace();
		}
	}

	// args[0] ---> Input File Name with folder
	// args[1] ---> GLANET installation folder with "\\" at the end. This folder
	// will be used for outputFolder and dataFolder.
	// args[2] ---> Input File Format
	// ---> default
	// Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	// --->
	// Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	// args[3] ---> Annotation, overlap definition, number of bases, default 1
	// args[4] ---> Enrichment parameter
	// ---> default Commons.DO_ENRICH
	// ---> Commons.DO_NOT_ENRICH
	// args[5] ---> Generate Random Data Mode
	// ---> default Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	// ---> Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT
	// args[6] ---> multiple testing parameter, enriched elements will be
	// decided and sorted with respest to this parameter
	// ---> default Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE
	// ---> Commons.BONFERRONI_CORRECTED_P_VALUE
	// args[7] ---> Bonferroni Correction Significance Level, default 0.05
	// args[8] ---> Benjamini Hochberg FDR, default 0.05
	// args[9] ---> Number of permutations, default 5000
	// args[10] ---> Dnase Enrichment
	// ---> default Commons.DO_NOT_DNASE_ENRICHMENT
	// ---> Commons.DO_DNASE_ENRICHMENT
	// args[11] ---> Histone Enrichment
	// ---> default Commons.DO_NOT_HISTONE_ENRICHMENT
	// ---> Commons.DO_HISTONE_ENRICHMENT
	// args[12] ---> Transcription Factor(TF) Enrichment
	// ---> default Commons.DO_NOT_TF_ENRICHMENT
	// ---> Commons.DO_TF_ENRICHMENT
	// args[13] ---> KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_KEGGPATHWAY_ENRICHMENT
	// args[14] ---> TF and KEGG Pathway Enrichment
	// ---> default Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	// args[15] ---> TF and CellLine and KeggPathway Enrichment
	// ---> default Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// ---> Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	// args[16] ---> RSAT parameter
	// ---> default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// ---> Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	// args[17] ---> job name example: ocd_gwas_snps
	// args[18] ---> writeGeneratedRandomDataMode checkBox
	// ---> default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	// ---> Commons.WRITE_GENERATED_RANDOM_DATA
	// args[19] ---> writePermutationBasedandParametricBasedAnnotationResultMode
	// checkBox
	// ---> default
	// Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// --->
	// Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	// args[20] ---> writePermutationBasedAnnotationResultMode checkBox
	// ---> default Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// ---> Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	// args[21] ---> number of permutations in each run
	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");

		humanGene2RefSeq( dataFolder);
		humanRNANucleotideAccession2GeneID( dataFolder);

	}

}
