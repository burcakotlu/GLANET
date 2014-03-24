package kegg;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class KeggPathwayParser {
	
	public String createOutputFileNames(String inputFileName){
		String baseName= "";
		
		Pattern pattern = 	Pattern.compile("hsa[0-9]*") ;
		Matcher matcher =	pattern.matcher(inputFileName);
		while (matcher.find()) {
//		      System.out.print("Start index: " + matcher.start());
//		      System.out.print(" End index: " + matcher.end() + " ");
//		      System.out.println(matcher.group());
		      baseName = matcher.group();
		      break;
		    }
		return baseName;		
	}
	
	
	private void parseXmlFile(String filename){
		
//		Get the factory
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
			try{
//				Using the factory get the instance of document builder
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				Document dom = db.parse(filename);
				
				String base = createOutputFileNames(filename);
				
				parseDocument(dom,base);
				
			}catch (ParserConfigurationException pce){
				pce.printStackTrace();
			} catch (SAXException se){
				se.printStackTrace();
			}catch (IOException ioe){
				ioe.printStackTrace();
			}
		}
		
	
	/**
	 * I take an entry element and read the values in, create
	 * an Entry object and return it
	 */
	private Entry getEntry(Element entryElement){
		//for each <entry> element get text or int values of
				//id, name, type, link,  reaction, component, graphics
		Entry entry = null;
		
		if(entryElement!=null){ 
			int id = Integer.parseInt(entryElement.getAttribute("id"));
			String name = entryElement.getAttribute("name");
			String type = entryElement.getAttribute("type");
			String link = entryElement.getAttribute("link");
			String reaction = entryElement.getAttribute("reaction");			
			
			entry = new Entry(id,name,type,link,reaction);
		}								
		return entry;		
	}
	
	private List<Entry> getEntryElements(Element docEle){
		NodeList  entryElements= docEle.getElementsByTagName("entry");
		
		List<Entry> entryList = new ArrayList<Entry>();
		
		if (entryElements!=null && entryElements.getLength()>0){
			for(int i =0; i<entryElements.getLength();i++){
//				get the entry element
				Element entryElement = (Element)entryElements.item(i);
//				get the entry object
				Entry entry = getEntry(entryElement);
//				Add it to the list
				entryList.add(entry);							
			}		
		}
		return entryList;	
	}
	
		private void parseDocument(Document dom,String base){
//			get the root element
			Element docEle = dom.getDocumentElement();		
			
//			get a nodelist of entry elements
			
			
			List<Entry>  entryList= getEntryElements(docEle);
											
			writePathwaytoGenes(entryList,base);
		}
		
//		Write the pathway and genes in that pathway	
		// Look at each entry if its type is gene then write each gene for this pathway to a file
			public void writePathwaytoGenes(List<Entry> entryList, String base){
				
				String gene = "gene";
				String geneList ="";
				String geneID= "";
				int startofGene=0;
				int indexofBlank =0;
				Set<String> setofGenes = new HashSet<String>();
				
				try{
					FileWriter fw = new FileWriter("C:\\eclipse_juno_workspace\\Doktora\\src\\kegg\\"+ base +"_PathwaytoGenes.txt");

					PrintWriter pw = new PrintWriter(fw);
			       
			        //Write to file for the first row
			        pw.print("Pathway");
			        pw.print("\t");
			        pw.print("Gene");
			        pw.print("\t");
			        pw.println();
			        	        
			        Entry entry = null;
					Iterator<Entry> it = entryList.iterator();
					
					while(it.hasNext()) {			
						
						entry =	(Entry)it.next();			
						try{
							if(entry!=null && entry.getType().equals(gene)){
								  	// print each gene on a separate line if there are more than one genes
							        geneList = entry.getName();
							        
							        indexofBlank = 0;
							        startofGene= 0;
							        
							        indexofBlank = geneList.indexOf(' ');
							        
							        if (indexofBlank>0){
							        	while (indexofBlank>0){
								        	geneID = geneList.substring(startofGene, indexofBlank);
								        								        
								        	if (!setofGenes.contains(geneID)){
								        		
								        		setofGenes.add(geneID);

								        		pw.print(base);
										        pw.print("\t");

										        pw.print(geneID);
										        pw.print("\t");	

										        pw.println();						        		
								        	}
									        
									        
									        startofGene = indexofBlank+1;
									        indexofBlank = geneList.indexOf(' ',indexofBlank+1);
								        }
							        	
							        	geneID = geneList.substring(startofGene, geneList.length());
							        	
							        	if (!setofGenes.contains(geneID)){
							        		
							        		setofGenes.add(geneID);
							        		
							        		pw.print(base);
									        pw.print("\t");
									        						        	
								        	pw.print(geneID);
									        pw.print("\t");	
									        
									        pw.println();
							        		
							        	}
							        	
							        }					        
							        else {
							        	
							        	if (!setofGenes.contains(geneList)){
							        		
							        		setofGenes.add(geneList);
							        		
								        	pw.print(base);
									        pw.print("\t");
									        						        
								        	pw.print(geneList);
									        pw.print("\t");	
									        
									        pw.println();
							        		
							        	}					        							        		
							        	
							        }
							        
								
							}
						}
						catch(NullPointerException npe){
							npe.printStackTrace();
						}
					}
					 //Flush the output to the file
			        pw.flush();
			       
			        //Close the Print Writer
			        pw.close();
			       
			        //Close the File Writer
			        fw.close();        
				} catch (IOException ioe){
					ioe.printStackTrace();
				}
				
			}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeggPathwayParser keggPathwayParser = new KeggPathwayParser();
		keggPathwayParser.parseXmlFile(args[0]);
	}

}
