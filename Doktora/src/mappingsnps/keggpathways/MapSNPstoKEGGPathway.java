/*
 * 
 * @author Burcak Otlu Saritas
 * 
 */

/*
 * This program requires kegg pathway names in a sequential order.
 * It reads the gene ids of that certain kegg pathway.
 * And it reads the snp ids of each gene id in that certain kegg pathway 
 * 
 * At the end, for each kegg pathway, the genes in that kegg pathway and snps in that genes are saved.
 * 
 */

package mappingsnps.keggpathways;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import ui.GlanetRunner;

public class MapSNPstoKEGGPathway {
	
	static HashMap<String,KeggPathway> keggPathwaysHashMap= new HashMap<String,KeggPathway>();
//	Kegg Pathways may contain the same genes
//	In order to reduce the number of snp downloads  for the same gene
//	Use this genetoSNP HashMap
//	As key use the geneID which is a string 
//	example geneID value is 10327
	HashMap<String,List<SNP>>   geneNametoListSNPHashMap = new HashMap<String, List<SNP>>();
	HashMap<String,Gene>   geneNametoGeneHashMap = new HashMap<String, Gene>();
	
	public void readKeggPathwayGeneFile(String fileName){
		
				
		try{
//			Open the file that is the first 
//			command line parameter
//			Input File is pathway_hsa.list
//			Each line contains pathwayName and geneName in that pathway and link type(I guess)
//			example line
//			path:hsa00030	hsa:64080	reverse
			  FileInputStream fstream = new FileInputStream(fileName);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));

			  String strLine;
			  
			  //Read File Line By Line			  
			  int startIndex = 0;
			  int firstIndexofTab = 0;
			  int secondIndexofTab = 0;
			  int indexofColon = 0;
			  
			  
			  KeggPathway keggPathway;		
			  Gene gene = null;
			  ArrayList<SNP> SNPList = null;
			  
			  String keggPathwayName = "";
			  String geneName ="";
			  String geneID = "";

			  
			  boolean keggPathwayHasChanged = false;
			  boolean firstKeggPathway = true;
			  
			  while ((strLine = br.readLine()) != null)   {				  
				  firstIndexofTab = strLine.indexOf('\t');
				  
				  
				  if (firstIndexofTab > 0){
					  // Kegg Pathway Name e.g: path:hsa00010
					  keggPathwayName = strLine.substring(startIndex, firstIndexofTab);
					  //Kegg Pathway name without path:
					// Kegg Pathway Name e.g: hsa00010					  
					  keggPathwayName = keggPathwayName.substring((keggPathwayName.indexOf(':'))+1,firstIndexofTab); 
					  
					  if (!keggPathwaysHashMap.containsKey(keggPathwayName)){
						  
						  keggPathway = new KeggPathway(keggPathwayName);
						  keggPathwaysHashMap.put(keggPathwayName, keggPathway);
						  
						  keggPathwayHasChanged =true;
						  
						  if (keggPathwayHasChanged && !firstKeggPathway){
//							  Kegg Pathway has finished
//							  Write it to the file
							  firstKeggPathway = false;
							  writeCompletedKeggPathwaytoFile(keggPathwayName);
						  }						  
						  
					  }else{
						  keggPathway = keggPathwaysHashMap.get(keggPathwayName);
					  }
					  
				  }else{
					  GlanetRunner.appendLog("Input File Format is not as accepted!");
					  break;
				  }
				  
				  secondIndexofTab = strLine.indexOf('\t',firstIndexofTab+1);
				  
				  if (secondIndexofTab > 0){
//					  example geneName hsa:10327
//					  get the 10327 out of hsa:10327
					  geneName = strLine.substring(firstIndexofTab+1,secondIndexofTab);
					  
					  indexofColon = strLine.indexOf(':', firstIndexofTab+1);
					  
					  if (indexofColon>0){
						  geneID= strLine.substring(indexofColon+1,secondIndexofTab);
					  }else{
						  GlanetRunner.appendLog("Input File Format is not as accepted!");
						  break;
					  }					  
					  
					  SNPList = new ArrayList<SNP>();
					  SNP snp = null;
					  
					  if (geneNametoGeneHashMap.containsKey(geneID)){
						  gene = geneNametoGeneHashMap.get(geneID);
						  						  
						  List<SNP> alreadyDownloadedSNPList = geneNametoListSNPHashMap.get(geneID);
						  keggPathway.getGenetoSNPs().put(gene,alreadyDownloadedSNPList);
					  } else{
						//@TODO  get the snps for this gene					  
						  final WebClient webClient = new WebClient();
						  String url = "http://www.ncbi.nlm.nih.gov/SNP/snp_ref.cgi?locusId=" + geneID;
						  final HtmlPage page = (HtmlPage) webClient.getPage(url);
						  List<DomElement> listDomElement = page.getElementsByName("Submit");
						  ListIterator<DomElement> listIterator = listDomElement.listIterator();
						  
						  while (listIterator.hasNext()){
							  DomElement element = listIterator.next();
							  
							  if (element.getAttribute("value").equals("Download")){
//								  GlanetRunner.appendLog("Download exist");
//								  GlanetRunner.appendLog(element.getClass());
								  if (element.getClass().toString().contains("HtmlSubmitInput")){
										 	HtmlSubmitInput htmlSubmitInput = (HtmlSubmitInput) element;
										  	Page saveFilePage = htmlSubmitInput.click();
										  	WebResponse webResponse = saveFilePage.getWebResponse();
										  	InputStream inputStream = webResponse.getContentAsStream();
//										  	@TODO write to a file									  	
											//home computer			  
//											FileWriter fileWriter = new FileWriter("C:\\eclipse_ganymede\\workspace\\Doktora1\\src\\mappingsnps\\keggpathways\\dbsnp_"+ geneID +".txt");
//											BufferedWriter out = new BufferedWriter(fileWriter);
											
											BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
											String stringLine;
											
											while ((stringLine = bufferedReader.readLine()) != null)   {
												snp = new SNP(stringLine);
												SNPList.add(snp);
											}
											  											  										  
//										  	out.close();
										  	inputStream.close();
//										  	@TODO write to a file
									  
								  }
								  
							  }
						  }
						  gene = new Gene(geneName,geneID);
						  gene.setSnpList(SNPList);
						  
						  geneNametoGeneHashMap.put(geneID, gene);
						  geneNametoListSNPHashMap.put(geneID, SNPList);
						  
						  keggPathway.getGenetoSNPs().put(gene, SNPList);					    
						  webClient.closeAllWindows();											  					 
//						  @todo end
						  						  
					  }					  					  					
					  					  				  
				  }else{
					  GlanetRunner.appendLog("Input File Format is not as accepted!");
					  break;
				  }									  
					 					  
			  } //end of Read
			  
			  //Close the input stream
			  in.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }		
	}

public void writeCompletedKeggPathwaytoFile(String keggPathwayName){
		
		try {
				
			KeggPathway keggPathway	= null;
//			FileWriter fileWriter 	= null;
//			BufferedWriter out1 		= null;
			String geneName 		= null;
			
			keggPathway = keggPathwaysHashMap.get(keggPathwayName);
			
				
				
//				home computer
				FileWriter fileWriter =  new FileWriter("C:\\eclipse_juno_workspace\\Doktora\\src\\mappingsnps\\keggpathways\\output_" +keggPathwayName+ ".txt");
				BufferedWriter out1 = new BufferedWriter(fileWriter);				
				
				HashMap<Gene,List<SNP>> hashMapGeneandSNPList = keggPathway.getGenetoSNPs();
				// more elegant way
				for (Map.Entry<Gene, List<SNP>> entry : hashMapGeneandSNPList.entrySet()) {						
					    Gene gene = entry.getKey();					    
					    geneName = gene.getName();
															
						List<SNP> list = entry.getValue();
						
						Iterator<SNP> itr = list.iterator();
						
						while(itr.hasNext()){
						 SNP snp = (SNP) itr.next();
						 out1.write(keggPathwayName + "--" + geneName + "--" + snp.getRsID()+"\n");
						} // snps in the gene
				}//gene in the kegg  	
				
				//Close the output stream
				out1.close();
				fileWriter.close();
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void writeKeggPathwaySNPsFiles(){
		
		try {
				
			KeggPathway keggPathway	= null;
//			FileWriter fileWriter 	= null;
//			BufferedWriter out1 		= null;
			String keggPathwayName 	= null;
			String geneName 		= null;
			
			for (Map.Entry<String, KeggPathway> keggPathwayEntry: keggPathwaysHashMap.entrySet()){
				
				keggPathwayName = keggPathwayEntry.getKey();
				keggPathway = keggPathwayEntry.getValue();
								
//				home computer
				FileWriter fileWriter =  new FileWriter("C:\\eclipse_juno_workspace\\Doktora\\src\\mappingsnps\\keggpathways\\output_" +keggPathwayName+ ".txt");
				BufferedWriter out1 = new BufferedWriter(fileWriter);				
				
				HashMap<Gene,List<SNP>> hashMapGeneandSNPList = keggPathway.getGenetoSNPs();
				// more elegant way
				for (Map.Entry<Gene, List<SNP>> entry : hashMapGeneandSNPList.entrySet()) {						
					    Gene gene = entry.getKey();					    
					    geneName = gene.getName();
															
						List<SNP> list = entry.getValue();
						
						Iterator<SNP> itr = list.iterator();
						
						while(itr.hasNext()){
						 SNP snp = (SNP) itr.next();
						 out1.write(keggPathwayName + "--" + geneName + "--" + snp.getRsID()+"\n");
						} // snps in the gene
				}//gene in the kegg  	
				
				//Close the output stream
				out1.close();
				fileWriter.close();
			}// for each kegg pathway
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		For each Kegg Pathway
//		For each gene in the given Kegg Pathway
//		get the list of snps on that gene
//		finally get the list of snps in the Kegg Pathway		
//		input file is pathway_hsa.list
		
			
		MapSNPstoKEGGPathway mapSNPstoKEGGPathway = new MapSNPstoKEGGPathway();
		mapSNPstoKEGGPathway.readKeggPathwayGeneFile(args[0]);
		mapSNPstoKEGGPathway.writeKeggPathwaySNPsFiles();
		
		
		
		
	}

}
