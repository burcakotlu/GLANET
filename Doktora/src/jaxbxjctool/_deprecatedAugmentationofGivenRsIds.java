/**
 * 
 */
package jaxbxjctool;

import gov.nih.nlm.ncbi.snp.docsum.Assembly;
import gov.nih.nlm.ncbi.snp.docsum.Component;
import gov.nih.nlm.ncbi.snp.docsum.MapLoc;
import gov.nih.nlm.ncbi.snp.docsum.Rs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import auxiliary.FileOperations;

import common.Commons;

/**
 * @author burcakotlu
 *
 */
public class _deprecatedAugmentationofGivenRsIds {
	
	
	private Unmarshaller unmarshaller;
	private static gov.nih.nlm.ncbi.snp.docsum.ObjectFactory _fool_javac=null;
	private  XMLInputFactory xmlInputFactory=null;
	

	//Example Data
	//7 NC_000007.13
	private Map<String,String> chrName2RefSeqIdforGrch37Map;

	public Map<String, String> getChrName2RefSeqIdforGrch37Map() {
		return chrName2RefSeqIdforGrch37Map;
	}


	public void setChrName2RefSeqIdforGrch37Map(
			Map<String, String> chrName2RefSeqIdforGrch37Map) {
		this.chrName2RefSeqIdforGrch37Map = chrName2RefSeqIdforGrch37Map;
	}


	public _deprecatedAugmentationofGivenRsIds() throws Exception
    {
//		//Construct map for refSeq Ids of homo sapiens chromosomes for GRCh37
//		String refSeqIdsforGRCh37InputFile = Commons.REFSEQ_IDS_FOR_GRCH37_INPUT_FILE;
//			
//		this.chrName2RefSeqIdforGrch37Map = fillMap(refSeqIdsforGRCh37InputFile);
		
		this.xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);
    	
		xmlInputFactory.setXMLResolver(new javax.xml.stream.XMLResolver()
        {
            	@Override
            	public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace)
                {
            		return new java.io.ByteArrayInputStream(new byte[0]);
                }
        });

		JAXBContext jaxbCtxt=JAXBContext.newInstance("gov.nih.nlm.ncbi.snp.docsum");
		this.unmarshaller=jaxbCtxt.createUnmarshaller();

    }
	
	
	public void run(String rsId,BufferedWriter bufferedWriter, List<String> searched, List<String> found) throws Exception
    {
         
		
		if(rsId.startsWith("rs")) 
			
			rsId=rsId.substring(2);
		
		 	//for debug
			searched.add(rsId);
			//for debug
			
			String uri="http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=snp&id="+rsId+"&retmode=xml";			
        
			XMLEventReader reader= xmlInputFactory.createXMLEventReader(new StreamSource(uri)); 
			
			while(reader.hasNext())
            {
				XMLEvent evt=reader.peek();

				if(!evt.isStartElement())
                {
					reader.nextEvent();
					continue;
                }

				StartElement start=evt.asStartElement();
				String localName=start.getName().getLocalPart();

				if(!localName.equals("Rs"))
                {
					reader.nextEvent();
					continue;
                }

				Rs rs=unmarshaller.unmarshal(reader, Rs.class).getValue();		
				
				
				for(Assembly as:rs.getAssembly())
                {  
					String groupLabel = as.getGroupLabel();
	                	
             	   	if(groupLabel!=null && groupLabel.startsWith("GRCh37")){
            		   
 
//                	   bufferedWriter.write(rs.getSequence().getObserved());
//                	   bufferedWriter.write("\t");
//                	    
//                       String eFetchString="http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id="+ refSeqId + "&seq_start="+ snpPositionMinusTen + "&seq_stop=" + snpPositionPlusTen + "&rettype=fasta&retmode=text";
//                       URL url= new URL(eFetchString);
//                       
//                       //System.out.println("EFETCH RESULT:");
//                       // Read from the URL
//                       try
//                       { 
//	                    	 BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
//	                         String inputLine;       // one line of the result, as it is read line by line
//	                         String sourceHTML= "";  // will eventually contain the whole result
//	                         // Continue to read lines while there are still some left to read
//	                         
//	                         while ((inputLine= in.readLine()) != null)  // read one line of the input stream
//		                         { sourceHTML+= inputLine + "\t";            // add this line to end of the whole shebang
////		                           ++lineCount;                              // count the number of lines read
//		                         }
//		                         
//	                         
//	                         // Close the connection
//	                         in.close();
//	                         // Display the entire page
//	                         bufferedWriter.write (sourceHTML);
//	                         bufferedWriter.write ("\t");
//    	                 
//                       }
//                       // Handle any error in opening the connection to the URL
//                       catch (Exception e)
//                       { System.out.println("Error reading from the URL:");
//                         System.out.println(e);
//                       }
//                       
//                       
//                     bufferedWriter.write(as.getGenomeBuild());
//                	   bufferedWriter.write("\t");
//                	   bufferedWriter.write(as.getGroupLabel());
//                	   bufferedWriter.write("\n");
//                	   bufferedWriter.write(comp.getAccession());
//                	   bufferedWriter.write("\t");
                       
                       
                       for(Component comp:as.getComponent())
                       {             
                      	   
	   	                   for(MapLoc maploc: comp.getMapLoc())
	   	                   {	                	   
	   	                	   if (maploc.getPhysMapInt()!=null){

	   	                		   //for debug
		   	            			found.add(rsId);
		   	            			//for debug
		   	                		   
		   	            			//eutil efetch returns 0-based coordinates
	   	                    	   int snpPositionZeroBased = maploc.getPhysMapInt();
	   	                    	   
//	   	                 		   bufferedWriter.write("rs"+rsId);
//	   	                    	   bufferedWriter.write("\t");

	   	                    	   String chrName = comp.getChromosome();
//	   	                    	   String refSeqId = this.getChrName2RefSeqIdforGrch37Map().get(chrName);
	   	                 	   
	   	                    	   //Add "chr" here
	   	                    	   bufferedWriter.write("chr" + chrName);
	   	                    	   bufferedWriter.write("\t");	   

	   	                    	   bufferedWriter.write(String.valueOf(snpPositionZeroBased));
		                           bufferedWriter.write("\t");
		                            
		                           bufferedWriter.write(String.valueOf(snpPositionZeroBased));
		                           bufferedWriter.write("\n");
		 		                	  		                	   
	   	                	   }//End of if maploc.getPhysMapInt() is not null
//	   	                	   else{
//	   	                		   //Note that for this rsId there is no snpPosition has been found
//	   	                		   System.out.println("Note that for this" + "\t" + rsId + "\t" +"there is no snpPosition has been found.");
//	   	                	   }
	   	                        
	   	                   }//End of for Maploc
                       }//End of for Component
                	          	                        		                             
            	  }//End of IF groupLabel startsWith "GRCh37"
					
                
                }//End of for Assembly
				
            }//End of while
			
        	reader.close();
    }


	public void readRsIDInputFileandWriteAugmentedOutputFile(String inputFileName, String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String rsId= null;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((rsId = bufferedReader.readLine())!=null){
//				this.run(rsId.substring(2),bufferedWriter);
			}
			
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
     
	}

	//Pay Attention
	//Contains X for chrX
	//Contains 1 for chr1
	
	//# Sequence-Name	Sequence-Role	Assigned-Molecule	Assigned-Molecule-Location/Type	GenBank-Accn	Relationship	RefSeq-Accn	Assembly-Unit
	//1	assembled-molecule	1	Chromosome	CM000663.1	=	NC_000001.10	Primary Assembly
	//X	assembled-molecule	X	Chromosome	CM000685.1	=	NC_000023.10	Primary Assembly
	public Map<String,String> fillMap(String refSeqIdsforGRCh37InputFile){
		
		Map<String,String> chrName2RefSeqIdforGrch37Map = new HashMap<String,String>();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int numberofChromosomesinHomoSapiens = 24;
		int count = 0;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String chrName;
		String refSeqId;
		
		try {
			fileReader = new FileReader(refSeqIdsforGRCh37InputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(strLine.startsWith("#")){
					continue;
				}else{
					if (count<numberofChromosomesinHomoSapiens){
						count++;
						
						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
						indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
						indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
						indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
						indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
						indexofSeventhTab 	= strLine.indexOf('\t', indexofSixthTab+1);
						
						chrName = strLine.substring(0, indexofFirstTab);
						refSeqId = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
						
						chrName2RefSeqIdforGrch37Map.put(chrName, refSeqId);
						continue;
						
					}
				}
					
				break;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chrName2RefSeqIdforGrch37Map;
	}
	
	
	public static void main(String[] args)
    {
			_deprecatedAugmentationofGivenRsIds app=null;
							
			try {
				app = new _deprecatedAugmentationofGivenRsIds();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
						
//			String refSeqIdsforGRCh37InputFile = Commons.REFSEQ_IDS_FOR_GRCH37_INPUT_FILE;
//			app.fillMap(refSeqIdsforGRCh37InputFile,chrName2RefSeqIdforGrch37Map);
			
	        String inputFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_RSIDS_INPUT_FILE_NAME_TEST;
	        String outputFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_RSIDS_OUTPUT_FILE_NAME;
	        
	        app.readRsIDInputFileandWriteAugmentedOutputFile(inputFileName,outputFileName);
	        	    
    }

}
