/**
 * @author burcakotlu
 * @date Apr 3, 2014 
 * @time 12:04:41 PM
 * 
 * This class with return the list of RsIds in a given interval.
 */
package jaxbxjctool;

import generated.ESearchResult;
import generated.Id;
import generated.IdList;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ui.GlanetRunner;

/**
 * 
 */
public class AugmentationofGivenIntervalwithRsIds {

	private Unmarshaller unmarshaller;
	// private static generated.ObjectFactory _fool_javac=null;
	private XMLInputFactory xmlInputFactory = null;

	public AugmentationofGivenIntervalwithRsIds() throws Exception {

		this.xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty( XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
		xmlInputFactory.setProperty( XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		xmlInputFactory.setProperty( XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);

		xmlInputFactory.setXMLResolver( new javax.xml.stream.XMLResolver() {

			@Override
			public Object resolveEntity( String publicID, String systemID, String baseURI, String namespace) {

				return new java.io.ByteArrayInputStream( new byte[0]);
			}
		});

		JAXBContext jaxbCtxt = JAXBContext.newInstance( "generated");
		this.unmarshaller = jaxbCtxt.createUnmarshaller();

	}

	// Requires oneBased positions
	// Requires chrName without preceeding "chr" string
	public List<Integer> getRsIdsInAGivenInterval(
			String chrNamewithoutPreceedingChr, 
			int givenIntervalStartOneBased,
			int givenIntervalEndOneBased) {

		List<Integer> rsIdList = new ArrayList<Integer>();
		XMLEventReader readerSearch = null;

		// esearch default retmode is xml or it can be set to json
		// chrName is without "chr", ex: 1, X, Y, 17...

		// Old way String
		// eSearchString="http://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=snp&term="+
		// givenIntervalStartOneBased + ":" + givenIntervalEndOneBased +
		// "[Base Position] AND "+ chrNamewithoutPreceedingChr
		// +"[CHR] AND txid9606&usehistory=n";
		// XMLEventReader readerSearch= xmlInputFactory.createXMLEventReader(new
		// StreamSource(eSearchString));

		try{

			// HTTP POST starts
			// String url ="http://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi";
			String termParameter = givenIntervalStartOneBased + ":" + givenIntervalEndOneBased + "[Base Position] AND " + chrNamewithoutPreceedingChr + "[CHR] AND txid9606";
			URI uri = null;
			uri = new URIBuilder().setScheme("https").setHost("www.ncbi.nlm.nih.gov").setPath("/entrez/eutils/esearch.fcgi").setParameter("db", "snp").setParameter("term", termParameter).setParameter("usehistory", "y").build();

			// http://wink.apache.org/1.0/api/org/apache/wink/client/ClientConfig.html
			RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).setConnectionRequestTimeout(60000).setStaleConnectionCheckEnabled(true).build();

			CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig( defaultRequestConfig).build();

			HttpPost post = new HttpPost(uri);
			post.addHeader("Content-Type", "application/xml");

			CloseableHttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();

			if(response.getEntity() != null){

				InputStream is = entity.getContent();
				readerSearch = xmlInputFactory.createXMLEventReader( is);
			}
			// HTTP POST ends

			while(readerSearch.hasNext()){
				XMLEvent evtSearch = readerSearch.peek();
				if(!evtSearch.isStartElement()){
					readerSearch.nextEvent();
					continue;
				}

				StartElement startSearch = evtSearch.asStartElement();
				String localNameSearch = startSearch.getName().getLocalPart();
				
				if(!localNameSearch.equals("eSearchResult")){
					readerSearch.nextEvent();
					continue;
				}

				ESearchResult eSearchResult = unmarshaller.unmarshal(readerSearch, ESearchResult.class).getValue();
				IdList idList = (IdList)eSearchResult.getCountOrRetMaxOrRetStartOrQueryKeyOrWebEnvOrIdListOrTranslationSetOrTranslationStackOrQueryTranslationOrERROR().get(5);

				for( Id id : idList.getId()){
					rsIdList.add(Integer.parseInt(id.getvalue()));
				}

			}// End of while

			readerSearch.close();
			httpclient.close();
			
		}catch( UnsupportedEncodingException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( ClientProtocolException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SocketException e){
			System.out.println("socket exception");
			e.printStackTrace();
		}catch( IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( XMLStreamException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( JAXBException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( URISyntaxException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rsIdList;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {

		AugmentationofGivenIntervalwithRsIds app = null;

		String chrName = "1";
		int startOneBased = 204924685;
		int endOneBased = 204924685;
		List<Integer> rsIdList = null;

		try{
			app = new AugmentationofGivenIntervalwithRsIds();
			rsIdList = app.getRsIdsInAGivenInterval( chrName, startOneBased, endOneBased);

			for( Integer rsId : rsIdList){
				GlanetRunner.appendLog( rsId);
			}

		}catch( Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
