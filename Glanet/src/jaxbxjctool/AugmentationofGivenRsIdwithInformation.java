/**
 * @author burcakotlu
 * @date Apr 3, 2014 
 * @time 11:18:28 AM
 * 
 * Augmentation of given RsId with information
 * Information means chrName, startZeroBased, endZeroBased and observedAlleles
 * 
 * So given an rsId without "rs" at the beginning
 * Using efetch eutil of ncbi, from snp db 
 * This information will be retrieved.
 * 
 * And will be returned in RsInformation class.
 *  
 */
package jaxbxjctool;

import gov.nih.nlm.ncbi.snp.docsum.Assembly;
import gov.nih.nlm.ncbi.snp.docsum.Component;
import gov.nih.nlm.ncbi.snp.docsum.MapLoc;
import gov.nih.nlm.ncbi.snp.docsum.Rs;
import gov.nih.nlm.ncbi.snp.docsum.Rs.MergeHistory;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import ui.GlanetRunner;
import common.Commons;
/**
 * 
 */
public class AugmentationofGivenRsIdwithInformation {
	
	private Unmarshaller unmarshaller;
//	private static gov.nih.nlm.ncbi.snp.docsum.ObjectFactory _fool_javac=null;
	private  XMLInputFactory xmlInputFactory=null;
	
		
	public AugmentationofGivenRsIdwithInformation() throws Exception
    {
		
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
	
	
	public int getTheNumberofBasesIntheObservedAlleles(String observedAllelesSeparatedwithSlash){
		int numberOfBasesAtMost = Integer.MIN_VALUE;
		int num;
		
			
		int indexofFormerSlash = observedAllelesSeparatedwithSlash.indexOf('/');
		int indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash +1);
		
		String allele;
		
		//for the first allele
		allele = observedAllelesSeparatedwithSlash.substring(0,indexofFormerSlash);
		
		//newly added starts
		num = allele.length();
		
		if (num>numberOfBasesAtMost){
			numberOfBasesAtMost = num;			
		}
		//newly added ends
		
		
				
		
		while (indexofFormerSlash>=0 && indexofLatterSlash >=0){
			
			allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1, indexofLatterSlash);	
			
			//newly added starts
			num = allele.length();
			
			if (num>numberOfBasesAtMost){
				numberOfBasesAtMost = num;			
			}
			//newly added ends
			
			
			indexofFormerSlash = indexofLatterSlash ;			
			indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash+1);
			
		}
		
		//for the last allele
		allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1);	
		
		//newly added starts
		num = allele.length();
		
		if (num>numberOfBasesAtMost){
			numberOfBasesAtMost = num;			
		}
		//newly added ends
		
		
		return numberOfBasesAtMost;
		
	}
	
	//24 Nov 2014
	public  List<RsInformation> getInformationforGivenRsIdList(String commaSeparatedRsIdList) throws XMLStreamException, JAXBException{
		
		int i= 1;
		RsInformation rsInformation;
		int numberofBasesInTheSNPAtMost = Integer.MIN_VALUE;
		List<RsInformation> rsInformationList = new ArrayList<RsInformation>();
     	
		String uri="http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=snp&id="+commaSeparatedRsIdList+"&retmode=xml";			
        
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
			
			
			//for debug purposes starts
			System.out.println(localName);
			//for debug purposes ends

			//@test localName toLowerCase is used
			if(!localName.equals("Rs"))
            {
				reader.nextEvent();
				continue;
            }
			
		
			Rs rs=unmarshaller.unmarshal(reader, Rs.class).getValue();	
			
			System.out.println(rs.getRsId() + " i: " + i++);
			
			
			for(Assembly as:rs.getAssembly())
            {  
				String groupLabel = as.getGroupLabel();
                	
         	   	if(groupLabel!=null && groupLabel.startsWith("GRCh38")){
        		   
                    
                   for(Component comp:as.getComponent())
                   {             
                  	   
   	                   for(MapLoc maploc: comp.getMapLoc())
   	                   {	                	   
   	                	   if (maploc.getPhysMapInt()!=null){
   	                		   
   	                		   rsInformation = new RsInformation();
   	                		   
   	                		   //starts 29th August 2014
	   	        				List<MergeHistory>  mergeHistoryList = rs.getMergeHistory();
	   	        				if (mergeHistoryList.size()>0){
	   	        					for (MergeHistory mergeHistory: mergeHistoryList){
	   	        						
	   	        
	   	        						if (rs.getRsId() == mergeHistory.getRsId()){
	   	        							rsInformation.setMerged(true);
	   	        							break;
	   	        						}
	   	        					}//End of for
	   	        					
	   	        				}
	   	        				//ends 29th August 2014
	   	        				
	   	        				
	   	        				//starts 31st August 2014
	   	        				//forward or reverse
	   	        				rsInformation.setOrient(maploc.getOrient());
	   	        				//ends 31st August 2014
   	                		  
   	                		   //set rsId
   	                		   rsInformation.setRsId(Commons.RS + rs.getRsId());
   	                		   
   	                		   //set chromosome name
   	                		   //This chrName is without "chr"
   	                		   //ex: 2, X, Y, 17
   	                		   rsInformation.setChrNamewithoutChr(comp.getChromosome());
   	                		   
   	                		   //set rsId start position
   	                		   //eutil efetch returns 0-based coordinates	   	                    	
   	                		   rsInformation.setStartZeroBased(maploc.getPhysMapInt());

   	                		   
   	                		   //set rsId observed Alleles
   	                		   rsInformation.setObservedAlleles(rs.getSequence().getObserved());
   	                		   
   	                		   numberofBasesInTheSNPAtMost = getTheNumberofBasesIntheObservedAlleles(rs.getSequence().getObserved());
   	                		   

   	                		   //set rsId end position
   	                		   //eutil efetch returns 0-based coordinates		   	                    	
   	                		   rsInformation.setEndZeroBased(maploc.getPhysMapInt()+numberofBasesInTheSNPAtMost-1);
   	                		  
   	                		   rsInformationList.add(rsInformation);
	   	                		   		 		                	  		                	   
   	                	   }//End of if maploc.getPhysMapInt() is not null
//   	                	   else{
//   	                		   //Note that for this rsId there is no snpPosition has been found
//   	                		   GlanetRunner.appendLog("Note that for this" + "\t" + rsId + "\t" +"there is no snpPosition has been found.");
//   	                	   }
   	                        
   	                   }//End of for Maploc
                   }//End of for Component
            	          	                        		                             
        	  }//End of IF groupLabel startsWith "GRCh37"
				
           
            }//End of for Assembly
			
        }//End of while
		
    	reader.close();
    	
    	return rsInformationList;
	}
	//24 NOV 2014
	
	//24 NOV 2014 starts
	public List<RsInformation> getInformationforGivenRsIdList(List<String> rsIdList) throws Exception{
		
		String commaSeparatedRsIdList = null;
		List<RsInformation>  rsInformationList= new ArrayList<RsInformation>();
		
		int numberofRsIdsSentInOneBatch = 100;
		
		//Set the number of Eutils Requests starts
		int numberofRequest =  rsIdList.size()/numberofRsIdsSentInOneBatch;
		
		int numberofRemaining = rsIdList.size() % numberofRsIdsSentInOneBatch;
		
		if ( numberofRemaining != 0){
			numberofRequest = numberofRequest+1;
		}
		//Set the number of Eutils Requests ends
		
		for(int i = 0; i < numberofRequest; i++){
			
			if (i == numberofRequest-1 && numberofRemaining!=0){
				
				//Set to empty string
				commaSeparatedRsIdList = "";
				
				for(int j =0; j<numberofRemaining-1 ;j++ ){
					commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i*numberofRsIdsSentInOneBatch+j) + Commons.COMMA ;
					
				}//End of for
				
				//Append the last rsID
				commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i*numberofRsIdsSentInOneBatch+(numberofRemaining-1));

			}else {
				
				//Set to empty string
				commaSeparatedRsIdList = "";
				
				//Make commaSeparatedRsIdList of at most numberofRsIdsInOneBatch elements
				//Get comma separated rsIDString
				for(int j =0; j<numberofRsIdsSentInOneBatch-1 ;j++ ){
					commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i*numberofRsIdsSentInOneBatch+j) + Commons.COMMA ;
					
				}//End of for
				
				//Append the last rsID
				commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i*numberofRsIdsSentInOneBatch+(numberofRsIdsSentInOneBatch-1));

			}
			
			rsInformationList.addAll(getInformationforGivenRsIdList(commaSeparatedRsIdList));
		}//End of for
		
		
		return rsInformationList;
		
	}
	//24 NOV 2014 ends

	
	public RsInformation getInformationforGivenRsId(String rsId) throws Exception
    {
		
			RsInformation rsInformation = null;
			int numberofBasesInTheSNPAtMost;
         		
			if(rsId.toLowerCase().startsWith(Commons.RS)) {
				rsId=rsId.substring(2);			
			}
								
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

				//@test localName toLowerCase is used
				if(!localName.toLowerCase().equals(Commons.RS))
                {
					reader.nextEvent();
					continue;
                }

				Rs rs=unmarshaller.unmarshal(reader, Rs.class).getValue();		
				
			
				
				for(Assembly as:rs.getAssembly())
                {  
					String groupLabel = as.getGroupLabel();
	                	
             	   	if(groupLabel!=null && groupLabel.startsWith("GRCh38")){
            		   
                        
                       for(Component comp:as.getComponent())
                       {             
                      	   
	   	                   for(MapLoc maploc: comp.getMapLoc())
	   	                   {	                	   
	   	                	   if (maploc.getPhysMapInt()!=null){
	   	                		   
	   	                		   rsInformation = new RsInformation();
	   	                		   
	   	                		   //starts 29th August 2014
		   	        				List<MergeHistory>  mergeHistoryList = rs.getMergeHistory();
		   	        				if (mergeHistoryList.size()>0){
		   	        					for (MergeHistory mergeHistory: mergeHistoryList){
		   	        						if (Integer.parseInt(rsId) == mergeHistory.getRsId()){
		   	        							rsInformation.setMerged(true);
		   	        							break;
		   	        						}
		   	        					}
		   	        					
		   	        				}
		   	        				//ends 29th August 2014
		   	        				
		   	        				
		   	        				//starts 31st August 2014
		   	        				//forward or reverse
		   	        				rsInformation.setOrient(maploc.getOrient());
		   	        				//ends 31st August 2014
	   	                		  
	   	                		   //set rsId
	   	                		   rsInformation.setRsId(rsId);
	   	                		   
	   	                		   //set chromosome name
	   	                		   //This chrName is without "chr"
	   	                		   //ex: 2, X, Y, 17
	   	                		   rsInformation.setChrNamewithoutChr(comp.getChromosome());
	   	                		   
	   	                		   //set rsId start position
	   	                		   //eutil efetch returns 0-based coordinates	   	                    	
	   	                		   rsInformation.setStartZeroBased(maploc.getPhysMapInt());

	   	                		   
	   	                		   //set rsId observed Alleles
	   	                		   rsInformation.setObservedAlleles(rs.getSequence().getObserved());
	   	                		   
	   	                		   numberofBasesInTheSNPAtMost = getTheNumberofBasesIntheObservedAlleles(rs.getSequence().getObserved());
	   	                		   

	   	                		   //set rsId end position
	   	                		   //eutil efetch returns 0-based coordinates		   	                    	
	   	                		   rsInformation.setEndZeroBased(maploc.getPhysMapInt()+numberofBasesInTheSNPAtMost-1);
	   	                		  
		   	                		   		 		                	  		                	   
	   	                	   }//End of if maploc.getPhysMapInt() is not null
//	   	                	   else{
//	   	                		   //Note that for this rsId there is no snpPosition has been found
//	   	                		   GlanetRunner.appendLog("Note that for this" + "\t" + rsId + "\t" +"there is no snpPosition has been found.");
//	   	                	   }
	   	                        
	   	                   }//End of for Maploc
                       }//End of for Component
                	          	                        		                             
            	  }//End of IF groupLabel startsWith "GRCh37"
					
               
                }//End of for Assembly
				
            }//End of while
			
        	reader.close();
        	
        	return rsInformation;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String rsId = "rs7534993";
		AugmentationofGivenRsIdwithInformation app = null;
		RsInformation test = null;
		
		try {
			app = new AugmentationofGivenRsIdwithInformation();
			test =app.getInformationforGivenRsId(rsId);
			
			if (test!=null){
				GlanetRunner.appendLog(test.getRsId());
				GlanetRunner.appendLog(test.getChrNamewithoutChr());
				GlanetRunner.appendLog(test.getStartZeroBased());
				GlanetRunner.appendLog(test.getEndZeroBased());
				GlanetRunner.appendLog(test.getObservedAlleles());

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		


	}

}
