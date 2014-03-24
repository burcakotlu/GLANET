/**
 * @author Burcak Otlu
 * Mar 7, 2014
 * 4:04:32 PM
 * 2014
 *
 * 
 */

/*
 * 
 * It lasts for 25 minutes.
 * It uses dbSNP flat files for augmentation with observed alleles
 * 
 * This way will not be used it is very costly.
 */
package processinputdata.augment;

import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

import dbSNP.CreationofChromosomeBasedSNPIntervalTrees;

public class AugmentationofProcessedInputDatawithdbSNP {

	/**
	 * 
	 */
	public AugmentationofProcessedInputDatawithdbSNP() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void fillChromosomeBasedSNPPositionsMap(String processedInputDataFileName, Map<String,List<SnpPosition>> chrName2SNPPositionsMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		String chrName;
		int zeroBasedStart;
		int zeroBasedEnd;
		
		List<SnpPosition> snpList;
		
		
		try {
			
			
			
			fileReader = new FileReader(processedInputDataFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				chrName = strLine.substring(0,indexofFirstTab);
				zeroBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
				zeroBasedEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				
				
				snpList = chrName2SNPPositionsMap.get(chrName);
				
				if(snpList == null){
					
					snpList = new ArrayList<SnpPosition>();
					
					SnpPosition snpPosition = new SnpPosition(zeroBasedStart,zeroBasedEnd);
					
					snpList.add(snpPosition);
					
					chrName2SNPPositionsMap.put(chrName, snpList);
					
				}else{
					
					SnpPosition snpPosition = new SnpPosition(zeroBasedStart,zeroBasedEnd);
					
					snpList.add(snpPosition);
					
					chrName2SNPPositionsMap.put(chrName, snpList);
					
				}
				
				
			}//End of while
			
					
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
	public static void augmentEachSNPwithDBSNP(Map<String,List<SnpPosition>> chrName2SNPPositionsMap,String augmentedwithdbSNPOutputFileName){
		
		IntervalTree dbSNPIntervalTree = null;
		String  chrName= null;
		List<SnpPosition> snpPositionList = null;
		List<IntervalTreeNode> overlappedNodeList = null;
		
		IntervalTreeNode root = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = FileOperations.createFileWriter(augmentedwithdbSNPOutputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			
			for(Map.Entry<String, List<SnpPosition>> entry:chrName2SNPPositionsMap.entrySet() ){

				//get the dbSNP interval tree
				chrName = entry.getKey();
				snpPositionList = entry.getValue();
				
				dbSNPIntervalTree = CreationofChromosomeBasedSNPIntervalTrees.readDBSNPFlatFileandCreateChromosomeBasedSNPIntervalTree(chrName);
				root = dbSNPIntervalTree.getRoot();
				
				
				for(SnpPosition snpPosition : snpPositionList){
					//bufferedWriter.write(chrName+ "\t" + snpPosition.getStart() +"\n");
					
					
					IntervalTreeNode node = new IntervalTreeNode(snpPosition.getStart(),snpPosition.getEnd());
					
					overlappedNodeList = new ArrayList<IntervalTreeNode>();
					
					//Overlaps
					dbSNPIntervalTree.findAllOverlappingIntervals(overlappedNodeList, root, node);
					
					if (overlappedNodeList.size()>0){
//						bufferedWriter.write("OverlapNodeListSize: " + overlappedNodeList.size() + "\n");
						
						for(IntervalTreeNode overlapNode:overlappedNodeList){
							bufferedWriter.write(overlapNode.getRsId() + "\t" + chrName + "\t" + snpPosition.getStart() + "\t");
							
							for(String observedAllele :overlapNode.getObservedVariationAlleles()){
								bufferedWriter.write(observedAllele +"\t");
								
							}//for every observed allele
							
							bufferedWriter.write("\n");
						}//for every overlap
						
					}//End of if 
				}//for every snp
				
				System.out.println(entry.getKey() +"\t" + entry.getValue().size());
				
				dbSNPIntervalTree = null;
			}//for every chromosome
			
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		

	}
	

	public static void augmentProcessedInputSNPswithdbSNP(String processedInputDataFileName,String augmentedwithdbSNPOutputFileName){
		
			
		Map<String,List<SnpPosition>> chrName2SNPPositionsMap = new HashMap<String,List<SnpPosition>>();
				
		fillChromosomeBasedSNPPositionsMap(processedInputDataFileName,chrName2SNPPositionsMap);
		
		augmentEachSNPwithDBSNP(chrName2SNPPositionsMap,augmentedwithdbSNPOutputFileName);
		
			
			
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//ProcessedInput already contains 0-based coordinates.
		String processedInputDataFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS;
		
		String augmentedwithdbSNPOutputFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_AUGMENTED_WITH_DBSNP;
		
		augmentProcessedInputSNPswithdbSNP(processedInputDataFileName,augmentedwithdbSNPOutputFileName);

	}

}
