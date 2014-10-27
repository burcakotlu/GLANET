package vcf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;



public class VCFParser {

	public void readVCFFile(String vcfFilename){		
		try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(vcfFilename);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  //home computer			  
			  FileWriter fileWriter = new FileWriter("C:\\eclipse_juno_workspace\\Doktora\\src\\vcf\\header.txt");
				  BufferedWriter out = new BufferedWriter(fileWriter);
			  
			  
//			  Read the header section
			  out.write("Header Section" + "\n");
			  while ((strLine = br.readLine()) != null)   {
			  // ADD the content to the ArrayList
				  out.write(strLine + "\n");
				  if (strLine.startsWith("#CHROM")){
					  out.write("Last Header File " + "\n");
					  out.write("The field definition line names eight mandatory columns," +
							  "corresponding to data columns representing the chromosome (CHROM)," +
							  "a 1-based position of the start of the variant (POS),"+
							  "unique identifiers of the variant (ID),"+
							  "the reference allele (REF),"+
							  "a comma separated list of alternate non-reference alleles (ALT)," +
							  "a phred-scaled quality score (QUAL),"+
							  "site filtering information (FILTER)"+
							  "and a semicolon separated list of additional, user extensible annotation (INFO)."+
							  "In addition, if samples are present in the file,"+
							  "the mandatory header columns are followed by a FORMAT column"+
							  "and an arbitrary number of sample IDs that define the samples included in the VCF file.\n");

					  if(strLine.contains("FORMAT"))
						  out.write("Contains Format " + "\n");					       
					  else 
						  out.write("Does not contain Format " + "\n");
					 break; 					  
				  }
			  }
			  
			  
			  
			  out.write("Data Section" + "\n");
			  
			  int firstIndexofTab;
			  int secondIndexofTab;
			  int thirdIndexofTab;
			  int fourthIndexofTab;
			  int fifthIndexofTab;
			  int sixthIndexofTab;
			  int seventhIndexofTab;
			  int eighthIndexofTab;
			  
			  int geneInfoIndex; 
			  int indexofEndofGeneInfo;
			  
			  String chromosomeNumber;
			  String position;
			  String snpID;
			  String ref;
			  String alt;
			  String quality;
			  String filter;
			  String info;
			  
			  String geneInfo = "";
			  
			  long numberofSNPs = 0;
			  long numberofSNPswithGeneInfo = 0;
			  long numberofSNPswithFilterDot =0;
			  long numberofSNPswithFilterPASS =0;
			  
			  String one	=	"1";
			  String two	=	"2";
			  String three	=	"3";
			  String four	=	"4";
			  String five	=	"5";
			  String six	=	"6";			  
			  String seven	=	"7";
			  String eight	=	"8";
			  String nine	=	"9";
			  String ten	=	"10";
			  String eleven	=	"11";
			  String twelve	=	"12";
			  String thirteen	=	"13";
			  String fourteen	=	"14";
			  String fifteen 	=	"15";
			  String sixteen	=	"16";
			  String seventeen	=	"17";
			  String eighteen	=	"18";
			  String nineteen	=	"19";
			  String twenty	=	"20";
			  String twentyone	=	"21";
			  String twentytwo	=	"22";
			  String X	=	"X";
			  String Y	=	"Y";
			  
			  
			  long numberofSNPsonChromosomeOne	=0;
			  long numberofSNPsonChromosomeTwo	=0;
			  long numberofSNPsonChromosomeThree	=0;
			  long numberofSNPsonChromosomeFour	=0;
			  long numberofSNPsonChromosomeFive	=0;
			  long numberofSNPsonChromosomeSix	=0;
			  long numberofSNPsonChromosomeSeven	=0;
			  long numberofSNPsonChromosomeEight	=0;
			  long numberofSNPsonChromosomeNine	=0;
			  long numberofSNPsonChromosomeTen	=0;
			  long numberofSNPsonChromosomeEleven	=0;
			  long numberofSNPsonChromosomeTwelve	=0;
			  long numberofSNPsonChromosomeThirteen	=0;
			  long numberofSNPsonChromosomeFourteen	=0;
			  long numberofSNPsonChromosomeFifteen	=0;
			  long numberofSNPsonChromosomeSixteen	=0;
			  long numberofSNPsonChromosomeSeventeen	=0;
			  long numberofSNPsonChromosomeEighteen	=0;
			  long numberofSNPsonChromosomeNineteen	=0;
			  long numberofSNPsonChromosomeTwenty	=0;
			  long numberofSNPsonChromosomeTwentyOne	=0;
			  long numberofSNPsonChromosomeTwentyTwo	=0;
			  long numberofSNPsonChromosomeX	=0;
			  long numberofSNPsonChromosomeY	=0;
			  long totalNumberofSNPsonChromosomes =0;
			  
			  
			  //Read the data section
			  while ((strLine = br.readLine()) != null)   {
				  // ADD the content to the ArrayList
//					  out.write(strLine + "\n");
					  
					  firstIndexofTab = strLine.indexOf('\t');
					  chromosomeNumber = strLine.substring(0, firstIndexofTab);		
					  
					  if (chromosomeNumber.equals(one))
						  numberofSNPsonChromosomeOne++;
					  else if (chromosomeNumber.equals(two))
						  numberofSNPsonChromosomeTwo++;
					  else if (chromosomeNumber.equals(three))
						  numberofSNPsonChromosomeThree++;
					  else if (chromosomeNumber.equals(four))
						  numberofSNPsonChromosomeFour++;
					  else if (chromosomeNumber.equals(five))
						  numberofSNPsonChromosomeFive++;
					  else if (chromosomeNumber.equals(six))
						  numberofSNPsonChromosomeSix++;
					  else if (chromosomeNumber.equals(seven))
						  numberofSNPsonChromosomeSeven++;
					  else if (chromosomeNumber.equals(eight))
						  numberofSNPsonChromosomeEight++;					  					  
					  else if (chromosomeNumber.equals(nine))
						  numberofSNPsonChromosomeNine++;
					  else if (chromosomeNumber.equals(ten))
						  numberofSNPsonChromosomeTen++;
					  else if (chromosomeNumber.equals(eleven))
						  numberofSNPsonChromosomeEleven++;
					  else if (chromosomeNumber.equals(twelve))
						  numberofSNPsonChromosomeTwelve++;
					  else if (chromosomeNumber.equals(thirteen))
						  numberofSNPsonChromosomeThirteen++;
					  else if (chromosomeNumber.equals(fourteen))
						  numberofSNPsonChromosomeFourteen++;
					  else if (chromosomeNumber.equals(fifteen))
						  numberofSNPsonChromosomeFifteen++;
					  else if (chromosomeNumber.equals(sixteen))
						  numberofSNPsonChromosomeSixteen++;
					  else if (chromosomeNumber.equals(seventeen))
						  numberofSNPsonChromosomeSeventeen++;
					  else if (chromosomeNumber.equals(eighteen))
						  numberofSNPsonChromosomeEighteen++;
					  else if (chromosomeNumber.equals(nineteen))
						  numberofSNPsonChromosomeNineteen++;
					  else if (chromosomeNumber.equals(twenty))
						  numberofSNPsonChromosomeTwenty++;
					  else if (chromosomeNumber.equals(twentyone))
						  numberofSNPsonChromosomeTwentyOne++;
					  else if (chromosomeNumber.equals(twentytwo))
						  numberofSNPsonChromosomeTwentyTwo++;
					  else if (chromosomeNumber.equals(X))
						  numberofSNPsonChromosomeX++;
					  else if (chromosomeNumber.equals(Y))
						  numberofSNPsonChromosomeY++;
					  					  
					  secondIndexofTab = strLine.indexOf('\t',firstIndexofTab+1);
					  position=strLine.substring(firstIndexofTab+1, secondIndexofTab);
					  
					  thirdIndexofTab = strLine.indexOf('\t',secondIndexofTab+1);
					  snpID = strLine.substring(secondIndexofTab+1, thirdIndexofTab);
					  
					  fourthIndexofTab = strLine.indexOf('\t',thirdIndexofTab+1);
//					  ref = strLine.substring(thirdIndexofTab+1, fourthIndexofTab);
					  
					  fifthIndexofTab = strLine.indexOf('\t',fourthIndexofTab+1);
//					  alt = strLine.substring(fourthIndexofTab+1, fifthIndexofTab);
					  
					  sixthIndexofTab = strLine.indexOf('\t',fifthIndexofTab+1);
//					  quality = strLine.substring(fifthIndexofTab+1, sixthIndexofTab);
					  
					  seventhIndexofTab = strLine.indexOf('\t',sixthIndexofTab+1);
					  filter = strLine.substring(sixthIndexofTab+1, seventhIndexofTab);
					  
					  if (filter.equals("."))
						  numberofSNPswithFilterDot++;
					  
					  if (filter.equals("PASS"))
						  numberofSNPswithFilterPASS++;
					  					  
					  eighthIndexofTab = strLine.indexOf('\t',seventhIndexofTab+1);
					  
					  if (eighthIndexofTab>0)
						  info = strLine.substring(seventhIndexofTab+1, eighthIndexofTab);
					  else 
						  info = strLine.substring(seventhIndexofTab+1, strLine.length());
					  
					  numberofSNPs++;
					  
					  geneInfoIndex = info.indexOf("GENEINFO");
					  if (geneInfoIndex > 0){
						  indexofEndofGeneInfo =  info.indexOf(";", geneInfoIndex);
						  geneInfo = info.substring(geneInfoIndex, indexofEndofGeneInfo);	
						  numberofSNPswithGeneInfo++;
					  }
					  
					 out.write("ChromNumber:" + chromosomeNumber +"\t"+ "POS:" + position +"\t"+ "SNPID:"+ snpID+ "\t" + "GeneInfo:" + geneInfo +"\n" );
										  
					  out.write("--------------------------------\n");
					 
				  }
			  totalNumberofSNPsonChromosomes = numberofSNPsonChromosomeOne + numberofSNPsonChromosomeTwo + numberofSNPsonChromosomeThree + numberofSNPsonChromosomeFour +numberofSNPsonChromosomeFive +numberofSNPsonChromosomeSix + numberofSNPsonChromosomeSeven + numberofSNPsonChromosomeEight + numberofSNPsonChromosomeNine + numberofSNPsonChromosomeTen + numberofSNPsonChromosomeEleven + numberofSNPsonChromosomeTwelve + numberofSNPsonChromosomeThirteen + numberofSNPsonChromosomeFourteen + numberofSNPsonChromosomeFifteen + numberofSNPsonChromosomeSixteen + numberofSNPsonChromosomeSeventeen + numberofSNPsonChromosomeEighteen + numberofSNPsonChromosomeNineteen + numberofSNPsonChromosomeTwenty + numberofSNPsonChromosomeTwentyOne + numberofSNPsonChromosomeTwentyTwo + numberofSNPsonChromosomeX + numberofSNPsonChromosomeY;
			  	
			  
			  out.write("Number of SNPs:" +numberofSNPs +"\n");
			  out.write("Number of SNPs with GeneInfo:" +numberofSNPswithGeneInfo +"\n");
			  out.write("Number of SNPs with Filter . :" +numberofSNPswithFilterDot +"\n");
			  out.write("Number of SNPs with Filter Pass :" +numberofSNPswithFilterPASS +"\n  \n");
			  
			  out.write("Number of SNPs on Chromosome One :" +numberofSNPsonChromosomeOne +"\n");
			  out.write("Number of SNPs on Chromosome Two :" +numberofSNPsonChromosomeTwo +"\n");
			  out.write("Number of SNPs on Chromosome Three :" +numberofSNPsonChromosomeThree +"\n");
			  out.write("Number of SNPs on Chromosome Four :" +numberofSNPsonChromosomeFour +"\n");
			  out.write("Number of SNPs on Chromosome Five :" +numberofSNPsonChromosomeFive +"\n");
			  out.write("Number of SNPs on Chromosome Six :" +numberofSNPsonChromosomeSix +"\n");
			  out.write("Number of SNPs on Chromosome Seven :" +numberofSNPsonChromosomeSeven +"\n");
			  out.write("Number of SNPs on Chromosome Eight :" +numberofSNPsonChromosomeEight +"\n");
			  out.write("Number of SNPs on Chromosome Nine :" +numberofSNPsonChromosomeNine +"\n");
			  out.write("Number of SNPs on Chromosome Ten :" +numberofSNPsonChromosomeTen +"\n");
			  out.write("Number of SNPs on Chromosome Eleven :" +numberofSNPsonChromosomeEleven +"\n");
			  out.write("Number of SNPs on Chromosome Twelve :" +numberofSNPsonChromosomeTwelve +"\n");
			  out.write("Number of SNPs on Chromosome Thirteen :" +numberofSNPsonChromosomeThirteen +"\n");
			  out.write("Number of SNPs on Chromosome Fourteen :" +numberofSNPsonChromosomeFourteen +"\n");
			  out.write("Number of SNPs on Chromosome Fifteen :" +numberofSNPsonChromosomeFifteen +"\n");
			  out.write("Number of SNPs on Chromosome Sixteen :" +numberofSNPsonChromosomeSixteen +"\n");
			  out.write("Number of SNPs on Chromosome Seventeen :" +numberofSNPsonChromosomeSeventeen +"\n");
			  out.write("Number of SNPs on Chromosome Eighteen :" +numberofSNPsonChromosomeEighteen +"\n");
			  out.write("Number of SNPs on Chromosome Nineteen :" +numberofSNPsonChromosomeNineteen +"\n");
			  out.write("Number of SNPs on Chromosome Twenty :" +numberofSNPsonChromosomeTwenty +"\n");
			  out.write("Number of SNPs on Chromosome Twenty One :" +numberofSNPsonChromosomeTwentyOne +"\n");
			  out.write("Number of SNPs on Chromosome Twenty Two :" +numberofSNPsonChromosomeTwentyTwo +"\n");
			  out.write("Number of SNPs on Chromosome X :" +numberofSNPsonChromosomeX +"\n");
			  out.write("Number of SNPs on Chromosome Y :" +numberofSNPsonChromosomeY +"\n\n");
			  
			  out.write("Total Number of SNPs one Chromosomes :" + totalNumberofSNPsonChromosomes +"\n");
			  
			  System.out.print("Number of SNPs:" +numberofSNPs +"\n");
			  System.out.print("Number of SNPs with GeneInfo:" +numberofSNPswithGeneInfo +"\n");
			  System.out.print("Number of SNPs with Filter . :" +numberofSNPswithFilterDot +"\n");
			  System.out.print("Number of SNPs with Filter Pass :" +numberofSNPswithFilterPASS +"\n\n");
			  
			  System.out.print("Number of SNPs on Chromosome One :" +numberofSNPsonChromosomeOne +"\n");
			  System.out.print("Number of SNPs on Chromosome Two :" +numberofSNPsonChromosomeTwo +"\n");
			  System.out.print("Number of SNPs on Chromosome Three :" +numberofSNPsonChromosomeThree +"\n");
			  System.out.print("Number of SNPs on Chromosome Four :" +numberofSNPsonChromosomeFour +"\n");
			  System.out.print("Number of SNPs on Chromosome Five :" +numberofSNPsonChromosomeFive +"\n");
			  System.out.print("Number of SNPs on Chromosome Six :" +numberofSNPsonChromosomeSix +"\n");
			  System.out.print("Number of SNPs on Chromosome Seven :" +numberofSNPsonChromosomeSeven +"\n");
			  System.out.print("Number of SNPs on Chromosome Eight :" +numberofSNPsonChromosomeEight +"\n");
			  System.out.print("Number of SNPs on Chromosome Nine :" +numberofSNPsonChromosomeNine +"\n");
			  System.out.print("Number of SNPs on Chromosome Ten :" +numberofSNPsonChromosomeTen +"\n");
			  System.out.print("Number of SNPs on Chromosome Eleven :" +numberofSNPsonChromosomeEleven +"\n");
			  System.out.print("Number of SNPs on Chromosome Twelve :" +numberofSNPsonChromosomeTwelve +"\n");
			  System.out.print("Number of SNPs on Chromosome Thirteen :" +numberofSNPsonChromosomeThirteen +"\n");
			  System.out.print("Number of SNPs on Chromosome Fourteen :" +numberofSNPsonChromosomeFourteen +"\n");
			  System.out.print("Number of SNPs on Chromosome Fifteen :" +numberofSNPsonChromosomeFifteen +"\n");
			  System.out.print("Number of SNPs on Chromosome Sixteen :" +numberofSNPsonChromosomeSixteen +"\n");
			  System.out.print("Number of SNPs on Chromosome Seventeen :" +numberofSNPsonChromosomeSeventeen +"\n");
			  System.out.print("Number of SNPs on Chromosome Eighteen :" +numberofSNPsonChromosomeEighteen +"\n");
			  System.out.print("Number of SNPs on Chromosome Nineteen :" +numberofSNPsonChromosomeNineteen +"\n");
			  System.out.print("Number of SNPs on Chromosome Twenty :" +numberofSNPsonChromosomeTwenty +"\n");
			  System.out.print("Number of SNPs on Chromosome Twenty One :" +numberofSNPsonChromosomeTwentyOne +"\n");
			  System.out.print("Number of SNPs on Chromosome Twenty Two :" +numberofSNPsonChromosomeTwentyTwo +"\n");
			  System.out.print("Number of SNPs on Chromosome X :" +numberofSNPsonChromosomeX +"\n");
			  System.out.print("Number of SNPs on Chromosome Y :" +numberofSNPsonChromosomeY +"\n\n");
			  
			  System.out.print("Total Number of SNPs one Chromosomes :" + totalNumberofSNPsonChromosomes +"\n");
			  
			  
			  
			  //Close the input stream
			  out.close();
			  in.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		VCFParser vCFParser = new VCFParser();
		vCFParser.readVCFFile(args[0]);

	}

}
