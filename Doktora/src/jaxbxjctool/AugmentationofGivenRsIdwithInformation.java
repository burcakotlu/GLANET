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
 * This information will be retrived.
 * 
 * And will be returned in RsInformation class.
 *  
 */
package jaxbxjctool;

import gov.nih.nlm.ncbi.snp.docsum.Assembly;
import gov.nih.nlm.ncbi.snp.docsum.Component;
import gov.nih.nlm.ncbi.snp.docsum.MapLoc;
import gov.nih.nlm.ncbi.snp.docsum.Rs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

/**
 * 
 */
public class AugmentationofGivenRsIdwithInformation {
	
	private Unmarshaller unmarshaller;
	private static gov.nih.nlm.ncbi.snp.docsum.ObjectFactory _fool_javac=null;
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
	
	public RsInformation getInformationforGivenRsId(String rsId) throws Exception
    {
		
			RsInformation rsInformation = null;
         		
			if(rsId.startsWith("rs")) {
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
            		   
                        
                       for(Component comp:as.getComponent())
                       {             
                      	   
	   	                   for(MapLoc maploc: comp.getMapLoc())
	   	                   {	                	   
	   	                	   if (maploc.getPhysMapInt()!=null){
	   	                		   
	   	                		   rsInformation = new RsInformation();
	   	                		  
	   	                		   //set rsId
	   	                		   rsInformation.setRsId(rsId);
	   	                		   
	   	                		   //set chromosome name
	   	                		   //This chrName is without "chr"
	   	                		   //ex: 2, X, Y, 17
	   	                		   rsInformation.setChrNamewithoutChr(comp.getChromosome());
	   	                		   
	   	                		   //set rsId start position
	   	                		   //eutil efetch returns 0-based coordinates	   	                    	
	   	                		   rsInformation.setStartZeroBased(maploc.getPhysMapInt());

	   	                		   //set rsId end position
	   	                		   //eutil efetch returns 0-based coordinates		   	                    	
	   	                		   rsInformation.setEndZeroBased(maploc.getPhysMapInt());
	   	                		   
	   	                		   //set rsId observed Alleles
	   	                		   rsInformation.setObservedAlleles(rs.getSequence().getObserved());

		   	                		   		 		                	  		                	   
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
				System.out.println(test.getRsId());
				System.out.println(test.getChrNamewithoutChr());
				System.out.println(test.getStartZeroBased());
				System.out.println(test.getEndZeroBased());
				System.out.println(test.getObservedAlleles());

			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		


	}

}
