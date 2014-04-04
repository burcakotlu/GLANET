/**
 * 
 */
package jaxbxjctool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import generated.ESearchResult;
import generated.Id;
import generated.IdList;
import auxiliary.FileOperations;

import common.Commons;

/**
 * @author burcakotlu
 *
 */
public class AugmentationofGivenChrNameandIntervals {
	
	
	
	private Unmarshaller unmarshaller;
	private static generated.ObjectFactory _fool_javac=null;
	private  XMLInputFactory xmlInputFactory=null;
	
	
	private AugmentationofGivenChrNameandIntervals() throws Exception
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

		JAXBContext jaxbCtxt=JAXBContext.newInstance("generated");
		this.unmarshaller=jaxbCtxt.createUnmarshaller();

    }
	
	
	//Pay Attention
	//Contains X for chrX
	//Contains 1 for chr1
	
	//# Sequence-Name	Sequence-Role	Assigned-Molecule	Assigned-Molecule-Location/Type	GenBank-Accn	Relationship	RefSeq-Accn	Assembly-Unit
	//1	assembled-molecule	1	Chromosome	CM000663.1	=	NC_000001.10	Primary Assembly
	//X	assembled-molecule	X	Chromosome	CM000685.1	=	NC_000023.10	Primary Assembly
	public void fillMap(String refSeqIdsforGRCh37InputFile,Map<String,String> chrName2RefSeqIdforGrch37Map){
		
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
	}
		
	private void run(String chrName, int chrPosition, BufferedWriter bufferedWriter) throws Exception
    {

		
		String eSearchString="http://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=snp&term="+ chrPosition + ":" + chrPosition + "[Base Position] AND "+ chrName +"[CHR] AND txid9606&usehistory=n";
           
       //esearch default retmode is xml or it can be set to json
       //how to parse esearch xml output
           
           
       //burcak yeni start
       XMLEventReader readerSearch= xmlInputFactory.createXMLEventReader(new StreamSource(eSearchString)); 
	
		while(readerSearch.hasNext())
        {
			XMLEvent evtSearch=readerSearch.peek();

			if(!evtSearch.isStartElement())
               {
				readerSearch.nextEvent();
				continue;
               }

			StartElement startSearch=evtSearch.asStartElement();
			String localNameSearch=startSearch.getName().getLocalPart();

			if(!localNameSearch.equals("eSearchResult"))
               {
				readerSearch.nextEvent();
				continue;
               }

			ESearchResult eSearchResult=unmarshaller.unmarshal(readerSearch, ESearchResult.class).getValue();
			IdList idList = (IdList) eSearchResult.getCountOrRetMaxOrRetStartOrQueryKeyOrWebEnvOrIdListOrTranslationSetOrTranslationStackOrQueryTranslationOrERROR().get(5);
			
			for(Id id: idList.getId()){
				System.out.println(id.getvalue());
				
			}
			
			System.out.println("come here");
			
       }//End of while
         

		readerSearch.close();
    }
		
		
	public void readChrNameandIntervalInputFileandWriteAugmentedOutputFile(String inputFileName, String outputFileName,Map<String,String> chrName2RefSeqIdforGrch37Map){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String chrNameandPosition= null;
		String chrName;
		int chrPosition;
		
		int indexofFirstTab;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((chrNameandPosition = bufferedReader.readLine())!=null){
				
				indexofFirstTab = chrNameandPosition.indexOf('\t');
				
				chrName = chrNameandPosition.substring(0,indexofFirstTab);
				chrPosition = Integer.parseInt(chrNameandPosition.substring(indexofFirstTab+1));
				
				this.run(chrName,chrPosition,bufferedWriter);
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

	//args[0] must have input file name with folder
	//args[1] must have GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2] must have Input File Format		
	//args[3] must have Number of Permutations	
	//args[4] must have False Discovery Rate (ex: 0.05)
	//args[5] must have Generate Random Data Mode (with GC and Mapability/without GC and Mapability)
	//args[6] must have writeGeneratedRandomDataMode checkBox
	//args[7] must have writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//args[8] must have writePermutationBasedAnnotationResultMode checkBox
	//args[9] must have Dnase Enrichment example: DO_DNASE_ENRICHMENT or DO_NOT_DNASE_ENRICHMENT
	//args[10] must have Histone Enrichment example : DO_HISTONE_ENRICHMENT or DO_NOT_HISTONE_ENRICHMENT
	//args[11] must have Tf and KeggPathway Enrichment example: DO_TF_KEGGPATHWAY_ENRICHMENT or DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	//args[12] must have Tf and CellLine and KeggPathway Enrichment example: DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT or DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[13] must have a job name exampe: any_string 
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;

		AugmentationofGivenChrNameandIntervals app=null;
		
		//Example Data
		//7 NC_000007.13
		Map<String,String> chrName2RefSeqIdforGrch37Map = new HashMap<String,String>();
		
		try {
			app = new AugmentationofGivenChrNameandIntervals();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Construct map for refSeq Ids of homo sapiens chromosomes for GRCh37
		String refSeqIdsforGRCh37InputFile = Commons.REFSEQ_IDS_FOR_GRCH37_INPUT_FILE;
		app.fillMap(refSeqIdsforGRCh37InputFile,chrName2RefSeqIdforGrch37Map);
		
        String inputFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_CHRNUMBER_BASEPAIRNUMBER;
        String outputFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_CHRNAME_AND_INTERVALS_OUTPUT_FILE_NAME;
        
        app.readChrNameandIntervalInputFileandWriteAugmentedOutputFile(inputFileName,outputFileName,chrName2RefSeqIdforGrch37Map);
        
	}

}
