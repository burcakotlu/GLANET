/**
 * @author Burcak Otlu
 * Nov 4, 2013
 * 11:04:19 AM
 * 2013
 *
 * 
 */
package comparison.binomialversuspermutation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Commons;

public class CompareBinomialVersusPermutation {

	/**
	 * 
	 */
	public CompareBinomialVersusPermutation() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void createMapfromInputFile(Map<String,Float> name2AdjustedPValueMap ,String inputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		String strLine;
		String name;
		Float adjustedPValue;
		int indexofFirstTab;
		int indexofSecondTab;
		
		
			try {
				fileReader = new FileReader(inputFileName);
				
				bufferedReader = new BufferedReader(fileReader);
					
				//Read from binomial file
				//Fill binomial hashMap
				while((strLine = bufferedReader.readLine())!=null){
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					
					name = strLine.substring(0, indexofFirstTab);
					adjustedPValue = null;
					
					//DNaseI, Transcription Factor or Histone
					if (indexofSecondTab==-1){
						adjustedPValue = Float.parseFloat(strLine.substring(indexofFirstTab+1));
					}
					//exon Based or regulation Based Kegg Pathway
					else{
						adjustedPValue = Float.parseFloat(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					}
						
					if (name2AdjustedPValueMap.get(name)==null) {
						name2AdjustedPValueMap.put(name, adjustedPValue);
													
					}else{
						System.out.println("Unexpected Duplicate Element");
					}		
					
				}//End of while
				
				bufferedReader.close();
						
					
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void createListandMapfromInputFile(List<ComparisonElement> elementList, Map<String,Float> name2AdjustedPValueMap ,String inputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		String strLine;
		String name;
		Float adjustedPValue;
		int indexofFirstTab;
		int indexofSecondTab;
		
		ComparisonElement element;
		
		
			try {
				fileReader = new FileReader(inputFileName);
				
				bufferedReader = new BufferedReader(fileReader);
					
				//Read from binomial file
				//Fill binomial hashMap
				while((strLine = bufferedReader.readLine())!=null){
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
					name = strLine.substring(0, indexofFirstTab);
					adjustedPValue = null;
					
					//DHSs, TF or Histone
					if (indexofSecondTab==-1){
						adjustedPValue = Float.parseFloat(strLine.substring(indexofFirstTab+1));
						
					}
					//exon Based Kegg Pathway and regulation Based Kegg Pathway
					else{
						adjustedPValue = Float.parseFloat(strLine.substring(indexofFirstTab+1,indexofSecondTab));
						
					}
					
					element = new ComparisonElement(name,adjustedPValue);
				
					
					if (name2AdjustedPValueMap.get(name)==null) {
						name2AdjustedPValueMap.put(name, adjustedPValue);
						elementList.add(element);
												
					}else{
						System.out.println("Unexpected Duplicate Element");
					}		
					
				}//End of while
				
				bufferedReader.close();
						
					
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	//For Positive Control K562 GATA1
	public void writeComparisonofPermutationTests(String caseStudyName,String supplementaryDataName,String firstLineName,String secondLineName,String thirdLineName,List<ComparisonElement> permutation_1000_without_Elements,
			Map<String,Float>  name2AdjustedPValuePermutation_10000_with_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_10000_without_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_5000_with_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_5000_without_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_1000_with_Map,
			String outputFileName){
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		String name;
		Float permutationAdjustedPValue_10000_with;
		Float permutationAdjustedPValue_10000_without;
		Float permutationAdjustedPValue_5000_with;
		Float permutationAdjustedPValue_5000_without;
		Float permutationAdjustedPValue_1000_with;
		Float permutationAdjustedPValue_1000_without;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		
		int count=1;
		int numberofEnrichedElements_PermutationTest_1000_without = 0;
		int numberofEnrichedElements_PermutationTest_1000_with = 0;
		int numberofEnrichedElements_PermutationTest_5000_without = 0;
		int numberofEnrichedElements_PermutationTest_5000_with = 0;
		int numberofEnrichedElements_PermutationTest_10000_without = 0;
		int numberofEnrichedElements_PermutationTest_10000_with = 0;
		
		
		
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//header
			bufferedWriter.write("\n");
			bufferedWriter.write(supplementaryDataName + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(caseStudyName + "\n");
			bufferedWriter.write("Comparison of Permutation Test Results\n");
			bufferedWriter.write("\n");
			bufferedWriter.write("Sorted with respect to Permutation Test, 1000 Permutations, withoutGCandMapability, Bonferroni Corrected P Value in Ascending Order.\n");
			bufferedWriter.write("Elements with Bonferroni Corrected P Value < 0.05 have been accepted as enriched elements.\n");
			bufferedWriter.write("\n");
						
			
			//write the information on the first page
			bufferedWriter.write(firstLineName + " " + secondLineName + " " + thirdLineName + " " + "Elements" 
					+ "\t"+ ""
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "1000" 
					+ "\t" + "1000" 
					+ "\t" + "5000" 
					+ "\t" + "5000" 
					+ "\t" + "10000" 
					+ "\t" + "10000" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					
					+ "\n");
			
			bufferedWriter.write(""
					+ "\t" + ""
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
						+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
							+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
							+ "\n");
			
			//Start with elements in binomialElements
			for(ComparisonElement element:permutation_1000_without_Elements){
				name = element.getName();
				permutationAdjustedPValue_1000_without = element.getAdjustedPValue();
				
				permutationAdjustedPValue_10000_with 	= name2AdjustedPValuePermutation_10000_with_Map.get(name);
				permutationAdjustedPValue_10000_without = name2AdjustedPValuePermutation_10000_without_Map.get(name);
				permutationAdjustedPValue_5000_with 	= name2AdjustedPValuePermutation_5000_with_Map.get(name);
				permutationAdjustedPValue_5000_without 	= name2AdjustedPValuePermutation_5000_without_Map.get(name);
				permutationAdjustedPValue_1000_with 	= name2AdjustedPValuePermutation_1000_with_Map.get(name);
				
				if(permutationAdjustedPValue_10000_with<0.05){
					numberofEnrichedElements_PermutationTest_10000_with++;
				}
				if(permutationAdjustedPValue_10000_without<0.05){
					numberofEnrichedElements_PermutationTest_10000_without++;
				}
				if(permutationAdjustedPValue_5000_with<0.05){
					numberofEnrichedElements_PermutationTest_5000_with++;
				}
				if(permutationAdjustedPValue_5000_without<0.05){
					numberofEnrichedElements_PermutationTest_5000_without++;
				}
				if(permutationAdjustedPValue_1000_with<0.05){
					numberofEnrichedElements_PermutationTest_1000_with++;
				}
				if(permutationAdjustedPValue_1000_without<0.05){
					numberofEnrichedElements_PermutationTest_1000_without++;
				}
				
				
				bufferedWriter.write(count++ +"\t" + name 	
											 +"\t" + df.format(permutationAdjustedPValue_1000_without) 
											 +"\t" + df.format(permutationAdjustedPValue_1000_with) 
											 +"\t" + df.format(permutationAdjustedPValue_5000_without) 
											 +"\t" + df.format(permutationAdjustedPValue_5000_with) 
											 +"\t" + df.format(permutationAdjustedPValue_10000_without) 
											 +"\t" + df.format(permutationAdjustedPValue_10000_with) 
											 +"\n");
				
				
				if(permutationAdjustedPValue_10000_with!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_10000_with_Map.remove(name);	
				}
				if(permutationAdjustedPValue_10000_without!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_10000_without_Map.remove(name);	
				}
				if(permutationAdjustedPValue_5000_with!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_5000_with_Map.remove(name);	
				}
				if(permutationAdjustedPValue_5000_without!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_5000_without_Map.remove(name);	
				}
				if(permutationAdjustedPValue_1000_with!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_1000_with_Map.remove(name);	
				}
				
				
							
			}//End of For
			
			bufferedWriter.write("Number of Enriched Elements" 
					 +"\t" + "" 	
					 +"\t" + numberofEnrichedElements_PermutationTest_1000_without
					 +"\t" + numberofEnrichedElements_PermutationTest_1000_with 
					 +"\t" + numberofEnrichedElements_PermutationTest_5000_without
					 +"\t" + numberofEnrichedElements_PermutationTest_5000_with 
					 +"\t" + numberofEnrichedElements_PermutationTest_10000_without 
					 +"\t" + numberofEnrichedElements_PermutationTest_10000_with 
					 +"\n");
			
			//write the information on the last page
			bufferedWriter.write(firstLineName + " " + secondLineName + " " + thirdLineName + " " + "Elements"
					+ "\t"+ ""
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "1000" 
					+ "\t" + "1000" 
					+ "\t" + "5000" 
					+ "\t" + "5000" 
					+ "\t" + "10000" 
					+ "\t" + "10000" 
					+ "\n");
			
			bufferedWriter.write(""
					+ "\t" + ""
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					
					+ "\n");
			
			bufferedWriter.write(""
					+ "\t" +  ""
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
						+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\n");
			
			
			
			//There must be no elements remained in the permutation maps unwritten
			if 	   (name2AdjustedPValuePermutation_10000_with_Map.size()>0 ||
					name2AdjustedPValuePermutation_10000_without_Map.size()>0 ||
					name2AdjustedPValuePermutation_5000_with_Map.size()>0 ||
					name2AdjustedPValuePermutation_5000_without_Map.size()>0 ||
					name2AdjustedPValuePermutation_1000_with_Map.size()>0
					){
					
					System.out.println("Unexpected Situation 3");
			}
			
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
	
	//for OCD GWAS Significant Snps
	public void writeComparisonofBinomialandPermutationTests(String caseStudyName,String supplementaryDataName,String firstLineName,String secondLineName,String thirdLineName,List<ComparisonElement>  binomialElements, 
			Map<String,Float>  name2AdjustedPValuePermutation_10000_with_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_10000_without_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_5000_with_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_5000_without_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_1000_with_Map,
			Map<String,Float>  name2AdjustedPValuePermutation_1000_without_Map,String outputFileName){
			
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		String name;
		Float binomialAdjustedPValue;
		Float permutationAdjustedPValue_10000_with;
		Float permutationAdjustedPValue_10000_without;
		Float permutationAdjustedPValue_5000_with;
		Float permutationAdjustedPValue_5000_without;
		Float permutationAdjustedPValue_1000_with;
		Float permutationAdjustedPValue_1000_without;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		
		int count=1;
		int numberofEnrichedElements_BinomialTest =0;
		int numberofEnrichedElements_PermutationTest_10000_with =0;
		int numberofEnrichedElements_PermutationTest_10000_without =0;
		int numberofEnrichedElements_PermutationTest_5000_with =0;
		int numberofEnrichedElements_PermutationTest_5000_without =0;
		int numberofEnrichedElements_PermutationTest_1000_with =0;
		int numberofEnrichedElements_PermutationTest_1000_without =0;
	
		
		
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//header
			bufferedWriter.write("\n");
			bufferedWriter.write(supplementaryDataName +"\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(caseStudyName + "\n");
			bufferedWriter.write("Comparison of Binomial Test and Permutation Test Results\n");
			bufferedWriter.write("\n");
			bufferedWriter.write("Sorted with respect to Binomial Test Bonferroni Corrected P Value in Ascending Order.\n");
			bufferedWriter.write("Elements with Bonferroni Corrected P Value < 0.05 have been accepted as enriched elements.\n");
			bufferedWriter.write("\n");
						
			//Write the test information on the first page
			bufferedWriter.write(firstLineName + " " + secondLineName + " " + thirdLineName + " " + "Elements"
					+ "\t"+ ""
					+ "\t" + "" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "" 
					+ "\t" + "1000" 
					+ "\t" + "1000" 
					+ "\t" + "5000" 
					+ "\t" + "5000" 
					+ "\t" + "10000" 
					+ "\t" + "10000" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					
					+ "\n");
			
			bufferedWriter.write(""
					+ "\t" + ""
					+ "\t" + "" 
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
						+ "\n");
			
			bufferedWriter.write(""
					+ "\t" + ""
					+ "\t" + "Binomial Test" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					
						+ "\n");
			
			bufferedWriter.write(""
					+ "\t" +  ""
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
					+ "\t" + "Bonferroni" 
							+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
					+ "\t" + "Corrected P Value" 
							+ "\n");
			//Start with elements in binomialElements
			for(ComparisonElement element:binomialElements){
				name = element.getName();
				binomialAdjustedPValue = element.getAdjustedPValue();
				
				permutationAdjustedPValue_10000_with 	= name2AdjustedPValuePermutation_10000_with_Map.get(name);
				permutationAdjustedPValue_10000_without = name2AdjustedPValuePermutation_10000_without_Map.get(name);
				permutationAdjustedPValue_5000_with 	= name2AdjustedPValuePermutation_5000_with_Map.get(name);
				permutationAdjustedPValue_5000_without 	= name2AdjustedPValuePermutation_5000_without_Map.get(name);
				permutationAdjustedPValue_1000_with 	= name2AdjustedPValuePermutation_1000_with_Map.get(name);
				permutationAdjustedPValue_1000_without 	= name2AdjustedPValuePermutation_1000_without_Map.get(name);
				
				if(binomialAdjustedPValue<0.05){
					numberofEnrichedElements_BinomialTest++;
				}
				if(permutationAdjustedPValue_10000_with<0.05){
					numberofEnrichedElements_PermutationTest_10000_with++;
				}
				if(permutationAdjustedPValue_10000_without<0.05){
					numberofEnrichedElements_PermutationTest_10000_without++;
				}
				if(permutationAdjustedPValue_5000_with<0.05){
					numberofEnrichedElements_PermutationTest_5000_with++;
				}
				if(permutationAdjustedPValue_5000_without<0.05){
					numberofEnrichedElements_PermutationTest_5000_without++;
				}
				if(permutationAdjustedPValue_1000_with<0.05){
					numberofEnrichedElements_PermutationTest_1000_with++;
				}
				if(permutationAdjustedPValue_1000_without<0.05){
					numberofEnrichedElements_PermutationTest_1000_without++;
				}
				
				bufferedWriter.write(count++ +"\t" + name 	
											 +"\t" + df.format(binomialAdjustedPValue) 
											 +"\t" + df.format(permutationAdjustedPValue_1000_without) 
											 +"\t" + df.format(permutationAdjustedPValue_1000_with) 
											 +"\t" + df.format(permutationAdjustedPValue_5000_without) 
											 +"\t" + df.format(permutationAdjustedPValue_5000_with) 
											 +"\t" + df.format(permutationAdjustedPValue_10000_without) 
											 +"\t" + df.format(permutationAdjustedPValue_10000_with) 
											 +"\n");
				
				if(permutationAdjustedPValue_10000_with!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_10000_with_Map.remove(name);	
				}
				if(permutationAdjustedPValue_10000_without!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_10000_without_Map.remove(name);	
				}
				if(permutationAdjustedPValue_5000_with!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_5000_with_Map.remove(name);	
				}
				if(permutationAdjustedPValue_5000_without!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_5000_without_Map.remove(name);	
				}
				if(permutationAdjustedPValue_1000_with!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_1000_with_Map.remove(name);	
				}
				if(permutationAdjustedPValue_1000_without!=null){
					//Remove this element from the permutation map
					name2AdjustedPValuePermutation_1000_without_Map.remove(name);	
				}
				
							
			}//End of For
			
			
			bufferedWriter.write("Number of Enriched Elements" 
					 +"\t" + "" 	
					 +"\t" + numberofEnrichedElements_BinomialTest
					 +"\t" + numberofEnrichedElements_PermutationTest_1000_without
					 +"\t" + numberofEnrichedElements_PermutationTest_1000_with 
					 +"\t" + numberofEnrichedElements_PermutationTest_5000_without
					 +"\t" + numberofEnrichedElements_PermutationTest_5000_with 
					 +"\t" + numberofEnrichedElements_PermutationTest_10000_without 
					 +"\t" + numberofEnrichedElements_PermutationTest_10000_with 
					 +"\n");
			
			//write the Test Information on the last page
			bufferedWriter.write(firstLineName + " " + secondLineName + " " + thirdLineName + " " + "Elements" 
					+ "\t"+ ""
					+ "\t" + "Binomial Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\t" + "Permutation Test" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "" 
					+ "\t" + "1000" 
					+ "\t" + "1000" 
					+ "\t" + "5000" 
					+ "\t" + "5000" 
					+ "\t" + "10000" 
					+ "\t" + "10000" 
					+ "\n");
			
			bufferedWriter.write("" 
					+ "\t" + ""
					+ "\t" + "" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					+ "\t" + "Permutations" 
					
					+ "\n");
			
			bufferedWriter.write(""
					+ "\t" + ""
					+ "\t" + "" 
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
					+ "\t" + "without" 
					+ "\t" + "with" 
						+ "\n");
			
			bufferedWriter.write(""
					+ "\t" + ""
					+ "\t" + "" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 
					+ "\t" + "GCandMapability" 				
					+ "\n");
			
			
		
			
			
			//There must be no elements remained in the permutation maps unwritten
			if 	   (name2AdjustedPValuePermutation_10000_with_Map.size()>0 ||
					name2AdjustedPValuePermutation_10000_without_Map.size()>0 ||
					name2AdjustedPValuePermutation_5000_with_Map.size()>0 ||
					name2AdjustedPValuePermutation_5000_without_Map.size()>0 ||
					name2AdjustedPValuePermutation_1000_with_Map.size()>0 ||
					name2AdjustedPValuePermutation_1000_without_Map.size()>0){
					
					System.out.println("Unexpected Situation 2");
			}
			
			//Close the buffer
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//For Positive Control
	public void writeComparisonofPermutationTests(	List<ComparisonElement> permutation_1000_without_Elements,
			Map<String,Float> name2AdjustedPValuePermutation_10000_with_Map,
			Map<String,Float> name2AdjustedPValuePermutation_10000_without_Map,
			Map<String,Float> name2AdjustedPValuePermutation_5000_with_Map,
			Map<String,Float> name2AdjustedPValuePermutation_5000_without_Map,
			Map<String,Float> name2AdjustedPValuePermutation_1000_with_Map,
			Map<String,Float> name2AdjustedPValuePermutation_1000_without_Map,
			String inputPermutation_10000_with_FileName,
			String inputPermutation_10000_without_FileName,
			String inputPermutation_5000_with_FileName,
			String inputPermutation_5000_without_FileName,
			String inputPermutation_1000_with_FileName,
			String inputPermutation_1000_without_FileName,
			String caseStudyName,
			String supplementaryDataName,
			String firstLineName,
			String secondLineName,
			String thirdLineName,
			String outputFileName){
		
		
		//clear elements in the list and maps
		permutation_1000_without_Elements.clear();
		name2AdjustedPValuePermutation_10000_with_Map.clear();		
		name2AdjustedPValuePermutation_10000_without_Map.clear();		
		name2AdjustedPValuePermutation_5000_with_Map.clear();		
		name2AdjustedPValuePermutation_5000_without_Map.clear();		
		name2AdjustedPValuePermutation_1000_with_Map.clear();		
		name2AdjustedPValuePermutation_1000_without_Map.clear();	
		
		createListandMapfromInputFile(permutation_1000_without_Elements,name2AdjustedPValuePermutation_1000_without_Map,inputPermutation_1000_without_FileName);
		createMapfromInputFile(name2AdjustedPValuePermutation_10000_with_Map,inputPermutation_10000_with_FileName);		
		createMapfromInputFile(name2AdjustedPValuePermutation_10000_without_Map,inputPermutation_10000_without_FileName);		
		createMapfromInputFile(name2AdjustedPValuePermutation_5000_with_Map,inputPermutation_5000_with_FileName);		
		createMapfromInputFile(name2AdjustedPValuePermutation_5000_without_Map,inputPermutation_5000_without_FileName);		
		createMapfromInputFile(name2AdjustedPValuePermutation_1000_with_Map,inputPermutation_1000_with_FileName);		
		//sort binomialElements with respect to adjusted p value in ascending order
		Collections.sort(permutation_1000_without_Elements, ComparisonElement.ADJUSTED_P_VALUE);		
		writeComparisonofPermutationTests(caseStudyName,supplementaryDataName,firstLineName,secondLineName,thirdLineName,permutation_1000_without_Elements,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				outputFileName);
	
		
	}

	
	
	//For OCD GWAS
	public void writeComparisonofBinomialandPermutationTests(	List<ComparisonElement> binomialElements,
										Map<String,Float> name2AdjustedPValueBinomialMap,
										Map<String,Float> name2AdjustedPValuePermutation_10000_with_Map,
										Map<String,Float> name2AdjustedPValuePermutation_10000_without_Map,
										Map<String,Float> name2AdjustedPValuePermutation_5000_with_Map,
										Map<String,Float> name2AdjustedPValuePermutation_5000_without_Map,
										Map<String,Float> name2AdjustedPValuePermutation_1000_with_Map,
										Map<String,Float> name2AdjustedPValuePermutation_1000_without_Map,
										String inputBinomialFileName,
										String inputPermutation_10000_with_FileName,
										String inputPermutation_10000_without_FileName,
										String inputPermutation_5000_with_FileName,
										String inputPermutation_5000_without_FileName,
										String inputPermutation_1000_with_FileName,
										String inputPermutation_1000_without_FileName,
										String caseStudyName,
										String supplementaryDataName,
										String firstLineName,
										String secondLineName,
										String thirdLineName,
										String outputFileName){
		//clear elements in the list and maps
			binomialElements.clear();
			name2AdjustedPValueBinomialMap.clear();
			name2AdjustedPValuePermutation_10000_with_Map.clear();		
			name2AdjustedPValuePermutation_10000_without_Map.clear();		
			name2AdjustedPValuePermutation_5000_with_Map.clear();		
			name2AdjustedPValuePermutation_5000_without_Map.clear();		
			name2AdjustedPValuePermutation_1000_with_Map.clear();		
			name2AdjustedPValuePermutation_1000_without_Map.clear();	
			
			createListandMapfromInputFile(binomialElements,name2AdjustedPValueBinomialMap,inputBinomialFileName);
			createMapfromInputFile(name2AdjustedPValuePermutation_10000_with_Map,inputPermutation_10000_with_FileName);		
			createMapfromInputFile(name2AdjustedPValuePermutation_10000_without_Map,inputPermutation_10000_without_FileName);		
			createMapfromInputFile(name2AdjustedPValuePermutation_5000_with_Map,inputPermutation_5000_with_FileName);		
			createMapfromInputFile(name2AdjustedPValuePermutation_5000_without_Map,inputPermutation_5000_without_FileName);		
			createMapfromInputFile(name2AdjustedPValuePermutation_1000_with_Map,inputPermutation_1000_with_FileName);		
			createMapfromInputFile(name2AdjustedPValuePermutation_1000_without_Map,inputPermutation_1000_without_FileName);		
			//sort binomialElements with respect to adjusted p value in ascending order
			Collections.sort(binomialElements, ComparisonElement.ADJUSTED_P_VALUE);		
			writeComparisonofBinomialandPermutationTests(caseStudyName,supplementaryDataName,firstLineName,secondLineName,thirdLineName,binomialElements,
					name2AdjustedPValuePermutation_10000_with_Map,
					name2AdjustedPValuePermutation_10000_without_Map,
					name2AdjustedPValuePermutation_5000_with_Map,
					name2AdjustedPValuePermutation_5000_without_Map,
					name2AdjustedPValuePermutation_1000_with_Map,
					name2AdjustedPValuePermutation_1000_without_Map,
					outputFileName);
		
	}
	
	//Compare permutation tests
	public void compareforPositiveControlK562GATA1(){
		//Elememt Name;
		String firstLineName = null;
		String secondLineName = null;
		String thirdLineName = null;
		
		//Case Study Name
		String caseStudyName = null;
		
		//Sumplementary Data Name 
		String supplementaryDataName = null;
		
		//permutation_1000_without list
		List<ComparisonElement> permutaion_1000_without_Elements = new ArrayList<ComparisonElement>();
			
		//permutation maps
		Map<String,Float> name2AdjustedPValuePermutation_10000_with_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_10000_without_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_5000_with_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_5000_without_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_1000_with_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_1000_without_Map = new HashMap<String,Float>();
		
		//input files
		String inputPermutation_10000_with_FileName;
		String inputPermutation_10000_without_FileName;
		String inputPermutation_5000_with_FileName;
		String inputPermutation_5000_without_FileName;
		String inputPermutation_1000_with_FileName;
		String inputPermutation_1000_without_FileName;
		
		//output file
		String outputFileName;
		
		//Positive Control K562 GATA1
		//2784 Genomic Intervals
		caseStudyName = "Case Study: 2784 Genomic Intervals of Positive Control K562 GATA1";
		
		
		//DNaseI elements
		//set the element name
		firstLineName = "DNaseI";
		secondLineName ="Hypersensitive Sites";
		thirdLineName =	"Cell Specific";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S6";
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.DNASE_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.DNASE_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1;	
		writeComparisonofPermutationTests(permutaion_1000_without_Elements,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
		
		
		//Transcription factor elements
		//set the element name
		firstLineName = "Transcription";
		secondLineName ="Factor and";
		thirdLineName ="Cell Specific";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S7";
				
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.TRANSCRIPTION_FACTOR_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1;			
		writeComparisonofPermutationTests(permutaion_1000_without_Elements,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
		
				
		//Histone elements
		//set the element name
		firstLineName = "Histone";
		secondLineName ="and";
		thirdLineName ="Cell Specific";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S8";
				
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.HISTONE_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.HISTONE_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1;		
		writeComparisonofPermutationTests(permutaion_1000_without_Elements,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
		
				
		//exonBasedKeggPathway elements
		//initialize input file names
		//set the element name
		firstLineName = "Exon Based";
		secondLineName ="Kegg Pathway";
		thirdLineName ="Gene-Set";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S9";
	
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.EXON_BASED_KEGG_PATHWAY_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1;		
		writeComparisonofPermutationTests(permutaion_1000_without_Elements,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
		
		
		
		//regulationBasedKeggPathway elements
		//initialize input file names
		//set the element name
		firstLineName = "Regulation Based";
		secondLineName ="Kegg Pathway";
		thirdLineName ="Gene-Set";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S10";
	
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.REGULATION_BASED_KEGG_PATHWAY_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1;		
		writeComparisonofPermutationTests(permutaion_1000_without_Elements,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
	}

	//Compare binomial test and permutation tests
	public void compareforOCDGWASSNPs(){

		//Elememt Name;
		String firstLineName = null;
		String secondLineName = null;
		String thirdLineName = null;
		
		//Case Study Name
		String caseStudyName = null;
		
		//Sumplementary Data Name 
		String supplementaryDataName = null;
		
		//binomial list
		List<ComparisonElement> binomialElements = new ArrayList<ComparisonElement>();
	
		//binomial map
		Map<String,Float> name2AdjustedPValueBinomialMap = new HashMap<String,Float>();
		
		//permutation maps
		Map<String,Float> name2AdjustedPValuePermutation_10000_with_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_10000_without_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_5000_with_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_5000_without_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_1000_with_Map = new HashMap<String,Float>();
		Map<String,Float> name2AdjustedPValuePermutation_1000_without_Map = new HashMap<String,Float>();
		
		//input files
		String inputBinomialFileName;
		String inputPermutation_10000_with_FileName;
		String inputPermutation_10000_without_FileName;
		String inputPermutation_5000_with_FileName;
		String inputPermutation_5000_without_FileName;
		String inputPermutation_1000_with_FileName;
		String inputPermutation_1000_without_FileName;
		
		//output file
		String outputFileName;
		
		
		//OCD GWAS 2340 Significant SNPs
		caseStudyName = "Case Study: 2340 Significant SNPs of OCD GWAS";
		
		
		//DNaseI elements
		//set the element name
		firstLineName = "DNaseI";
		secondLineName ="Hypersensitive Sites";
		thirdLineName ="Cell Specific";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S1";
		//initialize binomial input file name
		inputBinomialFileName = Commons.DNASE_ADJUSTED_P_VALUE_BINOMIAL_TEST;
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.DNASE_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.DNASE_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.DNASE_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS;	
		writeComparisonofBinomialandPermutationTests(binomialElements,
				name2AdjustedPValueBinomialMap,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputBinomialFileName,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
		
		
		//Transcription factor elements
		//set the element name
		firstLineName = "Transcription";
		secondLineName ="Factor and";
		thirdLineName ="Cell Specific";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S2";
				
		//initialize binomial input file name
		inputBinomialFileName = Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_BINOMIAL_TEST;
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.TRANSCRIPTION_FACTOR_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS;			
		writeComparisonofBinomialandPermutationTests(binomialElements,
				name2AdjustedPValueBinomialMap,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputBinomialFileName,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
		
				
		//Histone elements
		//set the element name
		firstLineName = "Histone";
		secondLineName ="and";
		thirdLineName ="Cell Specific";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S3";
				
		//initialize binomial input file name
		inputBinomialFileName = Commons.HISTONE_ADJUSTED_P_VALUE_BINOMIAL_TEST;
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.HISTONE_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.HISTONE_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.HISTONE_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS;		
		writeComparisonofBinomialandPermutationTests(binomialElements,
				name2AdjustedPValueBinomialMap,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputBinomialFileName,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
		
				
		//exonBasedKeggPathway elements
		//initialize input file names
		//set the element name
		firstLineName = "Exon Based";
		secondLineName ="Kegg Pathway";
		thirdLineName ="Gene-Set";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S4";
				
		//initialize binomial input file name
		inputBinomialFileName = Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_BINOMIAL_TEST;
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.EXON_BASED_KEGG_PATHWAY_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS;		
		writeComparisonofBinomialandPermutationTests(binomialElements,
				name2AdjustedPValueBinomialMap,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputBinomialFileName,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);
	
		
		
		
		//regulationBasedKeggPathway elements
		//initialize input file names
		//set the element name
		firstLineName = "Regulation Based";
		secondLineName ="Kegg Pathway";
		thirdLineName ="Gene-Set";
		//set supplementary data name
		supplementaryDataName ="Supplementary Table S5";
				
		//initialize binomial input file name
		inputBinomialFileName = Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_BINOMIAL_TEST;
		//initialize permutation input file names
		inputPermutation_10000_with_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_10000_without_FileName = Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_with_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_5000_without_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_with_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST;	
		inputPermutation_1000_without_FileName 	= Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST;	
		//initialize output file name
		outputFileName = Commons.REGULATION_BASED_KEGG_PATHWAY_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS;		
		writeComparisonofBinomialandPermutationTests(binomialElements,
				name2AdjustedPValueBinomialMap,
				name2AdjustedPValuePermutation_10000_with_Map,
				name2AdjustedPValuePermutation_10000_without_Map,
				name2AdjustedPValuePermutation_5000_with_Map,
				name2AdjustedPValuePermutation_5000_without_Map,
				name2AdjustedPValuePermutation_1000_with_Map,
				name2AdjustedPValuePermutation_1000_without_Map,
				inputBinomialFileName,
				inputPermutation_10000_with_FileName,
				inputPermutation_10000_without_FileName,
				inputPermutation_5000_with_FileName,
				inputPermutation_5000_without_FileName,
				inputPermutation_1000_with_FileName,
				inputPermutation_1000_without_FileName,
				caseStudyName,
				supplementaryDataName,
				firstLineName,
				secondLineName,
				thirdLineName,
				outputFileName);

		
	}
	/**
	 * @param args
	 * Each time takes two files
	 * Creates hashMaps for each file
	 * Write the adjusted p values of an element row by row by using these hashMaps
	 * Repeat this procedure for DNaseI, Transcription factor, Histone, exonBasedKeggPathway, regulationBasedKeggPathway
	 * 
	 */
	public static void main(String[] args) {
		
		CompareBinomialVersusPermutation compare = new CompareBinomialVersusPermutation();
		
		//OCD GWAS 2340 Significant SNPs
		compare.compareforOCDGWASSNPs();
		
		
		//POSITIVE CONTROL K562 GATA1 
		//2784 genomic intervals
		compare.compareforPositiveControlK562GATA1();
	
		
		
		
	}

}
