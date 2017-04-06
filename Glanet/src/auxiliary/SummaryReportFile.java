/**
 * 
 */
package auxiliary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import common.Commons;

import enumtypes.AnnotationFoundOverlapsOutputMode;
import enumtypes.AnnotationType;
import enumtypes.CommandLineArguments;
import enumtypes.PerformEnrichment;
import enumtypes.RegulatorySequenceAnalysisType;

/**
 * @author Burçak Otlu
 * @date Mar 9, 2017
 * @project Glanet 
 *
 */
public class SummaryReportFile {
	
	final static Logger logger = Logger.getLogger(SummaryReportFile.class);
	
	
	public static void writeSummaryReportFile(
			String outputFolder,
			AnnotationFoundOverlapsOutputMode annotationOutputMode,
			AnnotationType dnaseAnnotationType,
			AnnotationType histoneAnnotationType,
			AnnotationType tfAnnotationType,
			AnnotationType geneAnnotationType,
			AnnotationType bpGOTermsAnnotationType,
			AnnotationType mfGOTermsAnnotationType,
			AnnotationType ccGOTermsAnnotationType,
			AnnotationType keggPathwayAnnotationType,
			AnnotationType tfKeggPathwayAnnotationType,
			AnnotationType tfCellLineKeggPathwayAnnotationType,
			AnnotationType userDefinedGeneSetAnnotationType,
			AnnotationType userDefinedLibraryAnnotationType,
			PerformEnrichment performEnrichment,
			RegulatorySequenceAnalysisType rsa,
			Boolean thereIsLogFile){
		
		FileWriter fileWriter =  null;
		BufferedWriter bufferedWriter = null;
		
		String annotationOutputText = "";
		

		
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + System.getProperty("file.separator") + Commons.SUMMARY_REPORT_FILE);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"");
			bufferedWriter.write("\"http://www.w3.org/TR/html4/loose.dtd\">");
			bufferedWriter.write("<html>");
			bufferedWriter.write("<head>");
			bufferedWriter.write("<meta http-equiv=\"Content-Type\" content=\"text/html\"; charset=\"UTF-8\">");
			bufferedWriter.write("<title>GLANET Summary Report</title>");
			bufferedWriter.write("</head>");
			bufferedWriter.write("<body>");
			bufferedWriter.write("<h1>GLANET Summary Report:</h1>");
			bufferedWriter.write("<br>");
			bufferedWriter.write("<br>");
			
			
			
			switch(annotationOutputMode){
			
				case DO_WRITE_OVERLAPS_EACH_ONE_IN_SEPARATE_FILE_ELEMENT_BASED: 
					annotationOutputText=" element based files ";
					break;
					
				case DO_WRITE_OVERLAPS_ALL_IN_ONE_FILE_ELEMENT_TYPE_BASED:
					annotationOutputText=" element type based file ";
					break;
				
				default:
					break;
		
			}//End of switch
			
			
			
			//Annotation Element Based or Annotation Element Type Based
			if ( 	! performEnrichment.performEnrichmentWithoutAnnotation() &&
				    (annotationOutputMode.isWriteFoundOverlapsElementBased() || annotationOutputMode.isWriteFoundOverlapsElementTypeBased()) &&
					(dnaseAnnotationType.doDnaseAnnotation() || 
					histoneAnnotationType.doHistoneAnnotation() || 
					tfAnnotationType.doTFAnnotation() ||  
					geneAnnotationType.doGeneAnnotation() || 
					bpGOTermsAnnotationType.doBPGOTermsAnnotation() || 
					mfGOTermsAnnotationType.doMFGOTermsAnnotation() || 
					ccGOTermsAnnotationType.doCCGOTermsAnnotation() || 
					keggPathwayAnnotationType.doKEGGPathwayAnnotation() || 
					tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() || 
					tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() ||
					userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation() || 
					userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation())){
				
				bufferedWriter.write("<h2>Annotation:</h2>");
				bufferedWriter.write("<br>");
				
				//bufferedWriter.write("Annotation results are under " + outputFolder + Commons.ANNOTATION + System.getProperty("line.separator"));
				//bufferedWriter.write("<br>");
				
				//Dnase
				if (dnaseAnnotationType.doDnaseAnnotation() ){
					bufferedWriter.write("DNaseI hypersensitive sites annotation results with found overlaps in"  +  annotationOutputText +  "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.DNASE  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//TF
				if (tfAnnotationType.doTFAnnotation() 
						&& !tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()
						&& !tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
					bufferedWriter.write("Transcription factors binding sites annotation results with found overlaps in" + annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//Histone
				if (histoneAnnotationType.doHistoneAnnotation() ){
					bufferedWriter.write("Histone modification sites annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.HISTONE  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//Gene
				if (geneAnnotationType.doGeneAnnotation() ||  
					bpGOTermsAnnotationType.doBPGOTermsAnnotation() || mfGOTermsAnnotationType.doMFGOTermsAnnotation() || ccGOTermsAnnotationType.doCCGOTermsAnnotation() ||
					keggPathwayAnnotationType.doKEGGPathwayAnnotation() || tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() || tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() ){
					bufferedWriter.write("RefSeq genes GRCh37 (hg19) annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.HG19_REFSEQ_GENE  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//GO
				if (bpGOTermsAnnotationType.doBPGOTermsAnnotation() || mfGOTermsAnnotationType.doMFGOTermsAnnotation() || ccGOTermsAnnotationType.doCCGOTermsAnnotation() ){
					bufferedWriter.write("Gene Ontology terms annotation results with found overlaps in" + annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//KEGG
				if (keggPathwayAnnotationType.doKEGGPathwayAnnotation()
						&& !tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()
						&& !tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
					bufferedWriter.write("KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");					
				}
				
				//Joint TF KEGG
				if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() 
						&& !tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
					bufferedWriter.write("Transcription factors binding sites annotation results with found overlaps in" + annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");	
					bufferedWriter.write("Joint TF KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
				}
				
				//Joint TF Cell Line KEGG
				if (tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()
						&& !tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()){
					bufferedWriter.write("Transcription factors binding sites annotation results with found overlaps in" + annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("Joint TF Cell Line Based KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
				}
				
				//BOTH
				//Joint TF KEGG
				//Joint TF Cell Line KEGG
				if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() 
						&& tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
					bufferedWriter.write("Transcription factors binding sites annotation results with found overlaps in" + annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");	
					bufferedWriter.write("Joint TF KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("Joint TF Cell Line Based KEGG Pathway annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
				}

				//UDGS
				if (userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
					bufferedWriter.write("User defined gene set annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//UDL
				if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
					bufferedWriter.write("User defined library annotation results with found overlaps in" +  annotationOutputText + "are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}	
			}
			
			//Annotation Results files
			if (!performEnrichment.performEnrichmentWithoutAnnotation()){
				bufferedWriter.write("Result files with found association statistics are under " + outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.RESULTS  + System.getProperty("line.separator"));
				bufferedWriter.write("<br>");
			}
			
			
			//Enrichment
			if(performEnrichment.performEnrichment()){		
				
				bufferedWriter.write("<br>");		
				bufferedWriter.write("<h2>Enrichment:</h2>");
				bufferedWriter.write("<br>");
				
				
				//Dnase
				if (dnaseAnnotationType.doDnaseAnnotation() ){
					bufferedWriter.write("DNaseI hypersensitive sites enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.DNASE  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//TF
				if (tfAnnotationType.doTFAnnotation()
						&& !tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()
						&& !tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
					bufferedWriter.write("Transcription factors binding sites enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//Histone
				if (histoneAnnotationType.doHistoneAnnotation() ){
					bufferedWriter.write("Histone modification sites enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.HISTONE  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//Gene
				if (geneAnnotationType.doGeneAnnotation() ||  
					bpGOTermsAnnotationType.doBPGOTermsAnnotation() || mfGOTermsAnnotationType.doMFGOTermsAnnotation() || ccGOTermsAnnotationType.doCCGOTermsAnnotation() ||
					keggPathwayAnnotationType.doKEGGPathwayAnnotation() || tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() || tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() ){
					bufferedWriter.write("RefSeq genes GRCh37 (hg19) enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.HG19_REFSEQ_GENE  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//GO
				if (bpGOTermsAnnotationType.doBPGOTermsAnnotation() || mfGOTermsAnnotationType.doMFGOTermsAnnotation() || ccGOTermsAnnotationType.doCCGOTermsAnnotation() ){
					bufferedWriter.write("Gene Ontology terms enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.GENE_ONTOLOGY_TERMS  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//KEGG
				if (keggPathwayAnnotationType.doKEGGPathwayAnnotation() 
						&& !tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()
						&& !tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()) {
					bufferedWriter.write("KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//Joint TF KEGG
				if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()
						&& !tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation()){
					bufferedWriter.write("Transcription factors binding sites enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("Joint TF KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
				}
				
				//Joint TF CellLine KEGG
				if (tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() 
						&& !tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation()){
					bufferedWriter.write("Transcription factors binding sites enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("Joint TF Cell Line Based KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
				}
					
				//BOTH
				//Joint TF KEGG
				//Joint TF CellLine KEGG
				if (tfKeggPathwayAnnotationType.doTFKEGGPathwayAnnotation() && tfCellLineKeggPathwayAnnotationType.doTFCellLineKEGGPathwayAnnotation() ){
					bufferedWriter.write("Transcription factors binding sites enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.KEGG_PATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("Joint TF KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					bufferedWriter.write("Joint TF Cell Line Based KEGG Pathway enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.TF_CELLLINE_KEGGPATHWAY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
				}
				
				//UDGS
				if (userDefinedGeneSetAnnotationType.doUserDefinedGeneSetAnnotation()){
					bufferedWriter.write("User defined gene set enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.USER_DEFINED_GENESET  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
				//UDL
				if (userDefinedLibraryAnnotationType.doUserDefinedLibraryAnnotation()){
					bufferedWriter.write("User defined library enrichment results are under " + outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + Commons.USER_DEFINED_LIBRARY  + System.getProperty("line.separator"));
					bufferedWriter.write("<br>");
					
				}
				
			}
			
			if (rsa.isDoRegulatorySequenceAnalysisUsingRSAT()){
								
				bufferedWriter.write("<br>");
				bufferedWriter.write("<h2>Regulatory Sequence Analysis:</h2>");
				bufferedWriter.write("<br>");
				bufferedWriter.write("Regulatory Sequence results are under " + outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS + System.getProperty("file.separator") + Commons.RSAFileName);
				bufferedWriter.write("<br>");
				bufferedWriter.write("Post Analysis of Regulatory Sequence results are under " + outputFolder + Commons.REGULATORY_SEQUENCE_ANALYSIS + System.getProperty("file.separator") + Commons.RSAPostAnalysisFileName);
				bufferedWriter.write("<br>");
				
			}
			
			if(thereIsLogFile){
				
				bufferedWriter.write("<br>");
				bufferedWriter.write("<h2>Log File:</h2>");
				bufferedWriter.write("<br>");
				bufferedWriter.write("Log file is under the directory where GLANET.jar is executed." + System.getProperty("line.separator"));
				bufferedWriter.write("<br>");
				
			}
			
			
			bufferedWriter.write("</body>");
			bufferedWriter.write("</html>");
			
			
			
			bufferedWriter.write(System.getProperty("line.separator"));
			
			
			//Close
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		if (dnaseAnnotationType.doDnaseAnnotation()){
			
		}
		
		
		
		
	}
	



	public static void main(String[] args) {
		
		String outputFolder = args[CommandLineArguments.OutputFolder.value()];
		
		//Annotation		
		//ENCODE DNA Regulatory Elements
		AnnotationType dnaseAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.DnaseAnnotation.value()]);
		AnnotationType histoneAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.HistoneAnnotation.value()]);
		AnnotationType tfAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.TfAnnotation.value()]);
		
		//RefSeq Genes
		AnnotationType geneAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.GeneAnnotation.value()]);
		
		//GO Terms
		AnnotationType bpGOTermsAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.BPGOTermsAnnotation.value()]);
		AnnotationType mfGOTermsAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.MFGOTermsAnnotation.value()]);
		AnnotationType ccGOTermsAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.CCGOTermsAnnotation.value()]);
		
		//KEGG Pathway
		AnnotationType keggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.KeggPathwayAnnotation.value()]);
		
		//Joint TF-KEGG Pathway (Cell line pooled)
		AnnotationType tfKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.TfAndKeggPathwayAnnotation.value()]);
		
		//Joint TF-KEGG Pathway (Cell line based)
		AnnotationType tfCellLineKeggPathwayAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.CellLineBasedTfAndKeggPathwayAnnotation.value()]);
				
		//UDGS
		AnnotationType userDefinedGeneSetAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.UserDefinedGeneSetAnnotation.value()]);
		
		//UDL
		AnnotationType userDefinedLibraryAnnotationType = AnnotationType.convertStringtoEnum(args[CommandLineArguments.UserDefinedLibraryAnnotation.value()]);

		
		AnnotationFoundOverlapsOutputMode annotationOutputMode = AnnotationFoundOverlapsOutputMode.convertStringtoEnum(args[CommandLineArguments.AnnotationFoundOverlapsOutputMode.value()]);
		
		//Enrichment
		PerformEnrichment  performEnrichment = PerformEnrichment.convertStringtoEnum(args[CommandLineArguments.PerformEnrichment.value()]);
		
		//Regulatory Sequence Analysis
		RegulatorySequenceAnalysisType rsa = RegulatorySequenceAnalysisType.convertStringtoEnum(args[CommandLineArguments.RegulatorySequenceAnalysisUsingRSAT.value()]);
		
		Boolean thereIsLogFile = false;
		
		//5 April 2017
		if (args[CommandLineArguments.LogFile.value()].equalsIgnoreCase(Commons.ARG_LOG_FILE)){
			thereIsLogFile = true;
		}
		
		writeSummaryReportFile(
				outputFolder,
				annotationOutputMode,
				dnaseAnnotationType,
				histoneAnnotationType,
				tfAnnotationType,
				geneAnnotationType,
				bpGOTermsAnnotationType,
				mfGOTermsAnnotationType,
				ccGOTermsAnnotationType,
				keggPathwayAnnotationType,
				tfKeggPathwayAnnotationType,
				tfCellLineKeggPathwayAnnotationType,
				userDefinedGeneSetAnnotationType,
				userDefinedLibraryAnnotationType,
				performEnrichment,
				rsa,
				thereIsLogFile);
		

	}

}
