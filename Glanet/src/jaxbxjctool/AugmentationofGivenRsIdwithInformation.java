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

import org.apache.log4j.Logger;

import ui.GlanetRunner;

import common.Commons;

/**
 * 
 */
public class AugmentationofGivenRsIdwithInformation {

	final static Logger logger = Logger.getLogger(AugmentationofGivenRsIdwithInformation.class);

	private Unmarshaller unmarshaller;
	// private static gov.nih.nlm.ncbi.snp.docsum.ObjectFactory
	// _fool_javac=null;
	private XMLInputFactory xmlInputFactory = null;

	public AugmentationofGivenRsIdwithInformation() throws Exception {

		this.xmlInputFactory = XMLInputFactory.newInstance();
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		xmlInputFactory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, Boolean.TRUE);

		xmlInputFactory.setXMLResolver(new javax.xml.stream.XMLResolver() {
			@Override
			public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) {
				return new java.io.ByteArrayInputStream(new byte[0]);
			}
		});

		JAXBContext jaxbCtxt = JAXBContext.newInstance("gov.nih.nlm.ncbi.snp.docsum");
		this.unmarshaller = jaxbCtxt.createUnmarshaller();

	}

	// What does this method do?
	// We are looking at the length of the each of the observed alleles
	// Setting the numberOfBasesAtMost to the maximum length of observed alleles
	public int getTheNumberofBasesIntheObservedAlleles(String observedAllelesSeparatedwithSlash) {
		int numberOfBasesAtMost = Integer.MIN_VALUE;
		int num;

		int indexofFormerSlash = observedAllelesSeparatedwithSlash.indexOf('/');
		int indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/', indexofFormerSlash + 1);

		String allele;

		// First allele starts
		allele = observedAllelesSeparatedwithSlash.substring(0, indexofFormerSlash);

		num = allele.length();

		if (num > numberOfBasesAtMost) {
			numberOfBasesAtMost = num;
		}
		// First allele ends

		// Middle alleles starts
		while (indexofFormerSlash >= 0 && indexofLatterSlash >= 0) {

			allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash + 1, indexofLatterSlash);

			num = allele.length();

			if (num > numberOfBasesAtMost) {
				numberOfBasesAtMost = num;
			}

			indexofFormerSlash = indexofLatterSlash;
			indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/', indexofFormerSlash + 1);
		}
		// Middle alleles ends

		// Last allele starts
		allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash + 1);

		num = allele.length();

		if (num > numberOfBasesAtMost) {
			numberOfBasesAtMost = num;
		}
		// Last allele ends

		return numberOfBasesAtMost;

	}

	// 24 Nov 2014
	public List<RsInformation> getInformationforGivenRsIdList(String commaSeparatedRsIdList) {

		RsInformation rsInformation;
		int numberofBasesInTheSNPAtMost = Integer.MIN_VALUE;
		List<RsInformation> rsInformationList = new ArrayList<RsInformation>();

		/************************************************************/
		int lastSuccessfullRsID = Integer.MIN_VALUE;
		int indexofLastSuccessfulRSID = Integer.MIN_VALUE;
		int indexofCommaBeforeProblemRSID = Integer.MIN_VALUE;
		int indexofCommaAfterProblemRSID = Integer.MIN_VALUE;
		String problemRsId = null;
		RsInformation problemRsInformation = null;
		/************************************************************/

		String url = "http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=snp&id=" + commaSeparatedRsIdList + "&retmode=xml";

		XMLEventReader reader = null;

		try {

			reader = xmlInputFactory.createXMLEventReader(new StreamSource(url));

			while (reader.hasNext()) {
				XMLEvent evt = reader.peek();

				if (!evt.isStartElement()) {
					reader.nextEvent();
					continue;
				}

				StartElement start = evt.asStartElement();
				String localName = start.getName().getLocalPart();

				if (!localName.toLowerCase().equals(Commons.RS)) {
					reader.nextEvent();
					continue;
				}

				Rs rs = null;

				try {

					rs = unmarshaller.unmarshal(reader, Rs.class).getValue();

					for (Assembly as : rs.getAssembly()) {
						String groupLabel = as.getGroupLabel();

						if (groupLabel != null) {

							for (Component comp : as.getComponent()) {
								for (MapLoc maploc : comp.getMapLoc()) {
									if (maploc.getPhysMapInt() != null) {

										rsInformation = new RsInformation();

										// Set groupName
										rsInformation.setGroupLabel(groupLabel);

										
										// starts 31st August 2014
										// forward or reverse
										rsInformation.setOrient(maploc.getOrient());
										// ends 31st August 2014

										// set rsId
										rsInformation.setRsId(Commons.RS + rs.getRsId());

										// Set the last successful rsID
										lastSuccessfullRsID = rs.getRsId();

										// set chromosome name
										// This chrName is without "chr"
										// ex: 2, X, Y, 17
										rsInformation.setChrNameWithoutChr(comp.getChromosome());

										// set rsId start position
										// eutil efetch returns 0-based
										// coordinates
										rsInformation.setZeroBasedStart(maploc.getPhysMapInt());

										// set rsId observed Alleles
										rsInformation.setObservedAlleles(rs.getSequence().getObserved());

										numberofBasesInTheSNPAtMost = getTheNumberofBasesIntheObservedAlleles(rs.getSequence().getObserved());

										// set rsId end position
										// eutil efetch returns 0-based
										// coordinates
										rsInformation.setZeroBasedEnd(maploc.getPhysMapInt() + numberofBasesInTheSNPAtMost - 1);

										rsInformationList.add(rsInformation);

									}// End of if maploc.getPhysMapInt() is not
										// null

								}// End of for each Maploc

							}// End of for Component

						}// End of IF groupLabel startsWith "GRCh38"

					}// End of for Assembly

				} catch (JAXBException e) {
					// e.printStackTrace();
					logger.error(e.toString());

				} catch (NumberFormatException e) {
					// e.printStackTrace();

					// get the next rsId
					indexofLastSuccessfulRSID = commaSeparatedRsIdList.indexOf(Commons.RS + lastSuccessfullRsID);
					indexofCommaBeforeProblemRSID = commaSeparatedRsIdList.indexOf(',', indexofLastSuccessfulRSID + 1);
					indexofCommaAfterProblemRSID = commaSeparatedRsIdList.indexOf(',', indexofCommaBeforeProblemRSID + 1);

					if (indexofCommaAfterProblemRSID < 0) {
						problemRsId = commaSeparatedRsIdList.substring(indexofCommaBeforeProblemRSID + 1);
					} else {
						problemRsId = commaSeparatedRsIdList.substring(indexofCommaBeforeProblemRSID + 1, indexofCommaAfterProblemRSID);
					}

					logger.error(e.toString() + " for " + problemRsId);

					problemRsInformation = getInformationforGivenRsId(problemRsId);

					// If null then don't put it
					if (problemRsInformation != null) {
						rsInformationList.add(problemRsInformation);
					}// End of if not null
				}// End of catch

			}// End of while

			reader.close();

		} catch (XMLStreamException e) {
			// e.printStackTrace();
			logger.error(e.toString());
		}

		return rsInformationList;
	}

	// 24 NOV 2014

	// 24 NOV 2014 starts
	public List<RsInformation> getInformationforGivenRsIdList(List<String> rsIdList) {

		String commaSeparatedRsIdList = null;
		List<RsInformation> rsInformationList = new ArrayList<RsInformation>();

		int numberofRsIdsSentInOneBatch = 100;

		/**************************************************************/
		// Set the number of Eutils efetch Requests starts
		int numberofRequest = rsIdList.size() / numberofRsIdsSentInOneBatch;

		int numberofRemaining = rsIdList.size() % numberofRsIdsSentInOneBatch;

		if (numberofRemaining != 0) {
			numberofRequest = numberofRequest + 1;
		}
		// Set the number of Eutils efetch Requests ends
		/**************************************************************/

		/**************************************************************/
		for (int i = 0; i < numberofRequest; i++) {

			if (i == numberofRequest - 1 && numberofRemaining != 0) {

				// Set commaSeparatedRsIdList to empty string
				commaSeparatedRsIdList = "";

				for (int j = 0; j < numberofRemaining - 1; j++) {
					commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i * numberofRsIdsSentInOneBatch + j) + Commons.COMMA;

				}// End of for

				// Append the last rsID
				commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i * numberofRsIdsSentInOneBatch + (numberofRemaining - 1));

			} else {

				// Set commaSeparatedRsIdList to empty string
				commaSeparatedRsIdList = "";

				// Make commaSeparatedRsIdList of at most
				// numberofRsIdsInOneBatch elements
				// Get comma separated rsIDString
				for (int j = 0; j < numberofRsIdsSentInOneBatch - 1; j++) {
					commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i * numberofRsIdsSentInOneBatch + j) + Commons.COMMA;

				}// End of for

				// Append the last rsID
				commaSeparatedRsIdList = commaSeparatedRsIdList + rsIdList.get(i * numberofRsIdsSentInOneBatch + (numberofRsIdsSentInOneBatch - 1));

			}

			/**************************************************************/
			/***** GET rsInformation using NCBI EUTILS call starts *********/
			/**************************************************************/
			// Send ready commaSeparatedRsIdList
			// Get rsInformationList for commaSeparatedRsIdList
			// Add gathered rsInformationList to the existing rsInformationList
			rsInformationList.addAll(getInformationforGivenRsIdList(commaSeparatedRsIdList));
			/**************************************************************/
			/***** GET rsInformation using NCBI EUTILS call ends ***********/
			/**************************************************************/

		}// End of for
		/**************************************************************/

		return rsInformationList;

	}

	// 24 NOV 2014 ends

	// Even if you search for a merged rsID
	// It returns you a rs with a valid rsID
	public RsInformation getInformationforGivenRsId(String rsId) {

		RsInformation rsInformation = null;
		int numberofBasesInTheSNPAtMost;

		String uri = "http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=snp&id=" + rsId + "&retmode=xml";

		XMLEventReader reader;

		try {

			reader = xmlInputFactory.createXMLEventReader(new StreamSource(uri));

			while (reader.hasNext()) {
				XMLEvent evt = reader.peek();

				if (!evt.isStartElement()) {
					reader.nextEvent();
					continue;
				}

				StartElement start = evt.asStartElement();
				String localName = start.getName().getLocalPart();

				if (!localName.toLowerCase().equals(Commons.RS)) {
					reader.nextEvent();
					continue;
				}

				try {
					Rs rs = unmarshaller.unmarshal(reader, Rs.class).getValue();

					// 8 DEC 2014
					// NCBI EUtil has returned the latest valid rsID in the
					// given position
					// And NCBI returned rsId and given rsId does not match
					// Means that given rsId is a merged rsID
					// Valid rsID is returned rsId
					if (rs.getRsId() != Integer.parseInt(rsId)) {
						logger.debug("Given rsId: " + rsId + " and NCBI returned rsId: " + rs.getRsId() + "  check whether given rsId is merged");
						return null;
					}

					for (Assembly as : rs.getAssembly()) {
						String groupLabel = as.getGroupLabel();

						if (groupLabel != null) {

							for (Component comp : as.getComponent()) {

								for (MapLoc maploc : comp.getMapLoc()) {
									if (maploc.getPhysMapInt() != null) {

										rsInformation = new RsInformation();

										// Set groupLabel
										rsInformation.setGroupLabel(groupLabel);						

										// Set forward or reverse
										rsInformation.setOrient(maploc.getOrient());
										
										// Set rsId
										rsInformation.setRsId(Commons.RS + rs.getRsId());

										// Set chromosome name
										// This chrName is without "chr"
										// e.g.: 2, X, Y, 17
										rsInformation.setChrNameWithoutChr(comp.getChromosome());

										// set rsId start position
										// eutil efetch returns 0-based
										// coordinates
										rsInformation.setZeroBasedStart(maploc.getPhysMapInt());

										// set rsId observed Alleles
										rsInformation.setObservedAlleles(rs.getSequence().getObserved());

										numberofBasesInTheSNPAtMost = getTheNumberofBasesIntheObservedAlleles(rs.getSequence().getObserved());

										// Set rsId end position
										// NCBI EUTIL efetch returns 0-based coordinates
										rsInformation.setZeroBasedEnd(maploc.getPhysMapInt() + numberofBasesInTheSNPAtMost - 1);

									}// End of if maploc.getPhysMapInt() is not
								

								}// End of for each Maploc

							}// End of for each Component

						}// End of IF groupLabel startsWith "GRCh38"

					}// End of for Assembly
				} catch (NumberFormatException e) {
					// e.printStackTrace();
					logger.error(e.toString());
				}

			}// End of while

			reader.close();

		} catch (XMLStreamException e) {
			// e.printStackTrace();
			logger.error(e.toString() + " for " + rsId);

		} catch (JAXBException e) {
			// e.printStackTrace();
			logger.error(e.toString() + " for " + rsId);

		}
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
			test = app.getInformationforGivenRsId(rsId);

			if (test != null) {
				GlanetRunner.appendLog(test.getRsId());
				GlanetRunner.appendLog(test.getChrNameWithoutChr());
				GlanetRunner.appendLog(test.getZeroBasedStart());
				GlanetRunner.appendLog(test.getZeroBasedEnd());
				GlanetRunner.appendLog(test.getObservedAlleles());

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}