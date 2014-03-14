/*
 * This program searches the input tcga data in the chromosome based sorted refseq genes files.
 * It accomplishes linear search.
 * Looks for whether the alternate gene name(hugo symbol) and entrez gene id exists in the output lines. 
 * 
 * 
 */

package testtcgadata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.Commons;

import create.ChromosomeBasedFiles;
import create.encode.GeneType;
import create.encode.RegionofInterest;

public class TestTcgaData {

	List<RegionofInterest> regionofInterest = new ArrayList<RegionofInterest>();
	

		
//	Does the given region of interest intersect with the any tfbs
	public boolean intersect(int startPosition,int endPosition,int startPositionofRegionofInterest, int endPositionofRegionofInterest){
		
		int startPositionofIntersection = Math.max(startPosition, startPositionofRegionofInterest);
		int endPositionofIntersection = Math.min(endPosition, endPositionofRegionofInterest);
		
		if (startPositionofIntersection <= endPositionofIntersection)
			return true;
		else 
			return false;
	}
	

	

	public void searchforTestTCGAData(RegionofInterest region,BufferedWriter bw, String searchedAlternateGeneName,Integer searchedGeneId, GeneType geneType){
//		todo
		String chromName = region.getChromName();
		int startPositionofRegionofInterest = region.getStartPosition();
		int endPositionofRegionofInterest = region.getEndPosition();
		
		FileReader fileReader =null;
		BufferedReader br = null;
		
		String strLine;		
		
		int startPosition = 0;
		int endPosition = 0;
		
		String  refSeqGeneName;
		String intervalName;
//		char strand;
		String alternateGeneName;
		Integer geneId = null;
		
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		
		String outputString = "";
		String searchString = null;
		
		fileReader = ChromosomeBasedFiles.getSortedRefSeqGenesFileReader(chromName);
		
		 br = new BufferedReader(fileReader);

			try {
				
//				Data in the Sorted_Chromosome_Base_RefSeq_Gene_File is in ascending order for interval start field
//				a custom line from Sorted Chromosome Base Refseq Gene File
//				chrX	181384	271383	NR_027231	283981	5	+	LINC00685


				searchString = "Searched for " + chromName +"\t"+ startPositionofRegionofInterest + "\t" + endPositionofRegionofInterest+ "\t" + searchedAlternateGeneName + "\t" + searchedGeneId + "\n";
				
				while ((strLine = br.readLine()) != null)   {
					
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab = strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab+1);
										
					
					startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
//					Since the data is sorted with respect to start position field in ascending order
//					if start position of the region of interest is greater than the end position in the  Sorted_Chromosome_Base_Dnase_File
//					They can not intersect
//					Also there can not be any intersection for the rest of the data
					if (endPositionofRegionofInterest < startPosition)
						break;
					
					if (intersect(startPosition,endPosition,startPositionofRegionofInterest, endPositionofRegionofInterest)){
//						write this refseq gene to output file

//						get the data
//						example line
//						chr1	66999824	67000051	NM_032291	84251	Exon1	+	SGIP1

						refSeqGeneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						try{
						geneId = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
						} catch(NumberFormatException e){
							System.out.println(strLine.substring(indexofFourthTab+1, indexofFifthTab));
							System.out.println(strLine);							
						}
						intervalName = strLine.substring(indexofFifthTab+1,indexofSixthTab);
//						strand =strLine.substring(indexofSixthTab+1,indexofSeventhTab).charAt(0);
						alternateGeneName = strLine.substring(indexofSeventhTab+1);
						
						outputString = outputString.concat("Found" + "\t" + chromName + "\t" + startPosition + "\t" + endPosition + "\t"  + intervalName + "\t" +refSeqGeneName + "\t" + geneId + "\t" + alternateGeneName + "\n");						
					}
				} // End of While
				
				bw.write(searchString);
				bw.write(outputString);
				
				geneType.setNumberofGenes(geneType.getNumberofGenes()+1);
				
				if (searchedAlternateGeneName.equals("Unknown")){
					bw.write(searchedAlternateGeneName + "\t" + searchedGeneId + "\t" + "skip Unknown\n");
					geneType.setNumberofUnknownGenes(geneType.getNumberofUnknownGenes()+1);					
				} else if(outputString.contains(searchedGeneId.toString())){				
					bw.write(searchedGeneId + "\t" + "exists\n");
					geneType.setNumberofGenesIdExits(geneType.getNumberofGenesIdExits()+1);				
				}else if (outputString.contains(searchedAlternateGeneName)){
					bw.write("Gene ID does not exists but Gene Name" + "\t"+ searchedAlternateGeneName + "\t" + "exists\n");
					geneType.setNumberofGenesNameExits(geneType.getNumberofGenesNameExits()+1);									
				}else{
					bw.write(searchedAlternateGeneName +"\t" + searchedGeneId + "\t" + "does not exist\n");
					geneType.setNumberofGenesDoesNotExist(geneType.getNumberofGenesDoesNotExist()+1);
				}
				
				bw.write("--------------------------------------------\n");
				
//				close the buffered reader
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				

		
	}	
	
	public void testInputData(String inputFileName, String outputFileName){
		FileReader fileReader = null;
		FileWriter fileWriter = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine =null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0 ;
		
		String chromName;
		String geneHugoSymbol;
		Integer geneEntrezId;
		int startPos;
		int endPos;
		
		
		GeneType geneType = new GeneType();
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new  BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				
//				example line
//				chr1	1430871	1430871	ATAD3B	83858

				chromName = strLine.substring(0,indexofFirstTab);
				startPos = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				endPos = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
				
				geneHugoSymbol = strLine.substring(indexofThirdTab+1,indexofFourthTab);
				geneEntrezId = Integer.parseInt(strLine.substring(indexofFourthTab+1));				
				
				RegionofInterest region = new RegionofInterest();
				region.setChromName(chromName);
				region.setStartPosition(startPos);
				region.setEndPosition(endPos);
				
				
				searchforTestTCGAData(region, bufferedWriter, geneHugoSymbol,geneEntrezId,geneType);
					
			}
			
			bufferedWriter.write("Number of genes  that are searched\t" + geneType.getNumberofGenes() +"\n");
			bufferedWriter.write("Number of genes  that are unknown\t" + geneType.getNumberofUnknownGenes()+"\n");
			bufferedWriter.write("Number of genes  id that exists\t" + geneType.getNumberofGenesIdExits()+"\n");
			bufferedWriter.write("Number of genes  name that exists\t" + geneType.getNumberofGenesNameExits()+"\n");
			bufferedWriter.write("Number of genes  that does not exist\t" + geneType.getNumberofGenesDoesNotExist()+"\n");
			
			
			bufferedReader.close();
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args){
		TestTcgaData testTcgaData = new TestTcgaData();
		
//		Test for TCGA Test Data
		testTcgaData.testInputData(Commons.SEARCH_INPUT_FILE_FOR_TCGA_TEST_DATA, Commons.SEARCH_OUTPUT_FILE_FOR_TCGA_TEST_DATA);
		
		
	}
}
