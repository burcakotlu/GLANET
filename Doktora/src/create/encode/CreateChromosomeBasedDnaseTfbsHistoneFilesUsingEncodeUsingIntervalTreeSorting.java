/**
 * @author Burcak Otlu
 *
 * 
 */


/*
 * This program created unsorted and sorted chromosome based dnase, tfbs and histone intervals using ENCODE data
 * For sorting it uses Interval Tree Infix Traversal.
 * 
 * It takes 6 minutes.
 * 
 */


package create.encode;

import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import common.Commons;


public class CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting {
	
//	global variables
   IntervalTree tfbsIntervalTree = null;
   IntervalTree histoneIntervalTree = null;
   IntervalTree dnaseIntervalTree = null;
   
   Commons commons = new Commons();
   
// Write the dnase into corresponding chromosome file  
   public void writetoUnsortedChrBaseDnaseFile(Dnase dnase){
	   try {
			if(dnase.getChromName().equals("chr1")){
				commons.getChr1BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr1(commons.getNumberofDnaseinChr1()+1);
			}else if(dnase.getChromName().equals("chr2")){
				commons.getChr2BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr2(commons.getNumberofDnaseinChr2()+1);
			}else if(dnase.getChromName().equals("chr3")){
				commons.getChr3BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr3(commons.getNumberofDnaseinChr3()+1);
			}else if(dnase.getChromName().equals("chr4")){
				commons.getChr4BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr4(commons.getNumberofDnaseinChr4()+1);
			}else if(dnase.getChromName().equals("chr5")){
				commons.getChr5BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr5(commons.getNumberofDnaseinChr5()+1);
			}else if(dnase.getChromName().equals("chr6")){
				commons.getChr6BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr6(commons.getNumberofDnaseinChr6()+1);
			}else if(dnase.getChromName().equals("chr7")){
				commons.getChr7BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr7(commons.getNumberofDnaseinChr7()+1);
			}else if(dnase.getChromName().equals("chr8")){
				commons.getChr8BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr8(commons.getNumberofDnaseinChr8()+1);
			}else if(dnase.getChromName().equals("chr9")){
				commons.getChr9BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr9(commons.getNumberofDnaseinChr9()+1);
			}else if(dnase.getChromName().equals("chr10")){
				commons.getChr10BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr10(commons.getNumberofDnaseinChr10()+1);
			}else if(dnase.getChromName().equals("chr11")){
				commons.getChr11BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr11(commons.getNumberofDnaseinChr11()+1);
			}else if(dnase.getChromName().equals("chr12")){
				commons.getChr12BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr12(commons.getNumberofDnaseinChr12()+1);
			}else if(dnase.getChromName().equals("chr13")){
				commons.getChr13BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr13(commons.getNumberofDnaseinChr13()+1);
			}else if(dnase.getChromName().equals("chr14")){
				commons.getChr14BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr14(commons.getNumberofDnaseinChr14()+1);
			}else if(dnase.getChromName().equals("chr15")){
				commons.getChr15BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr15(commons.getNumberofDnaseinChr15()+1);
			}else if(dnase.getChromName().equals("chr16")){
				commons.getChr16BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr16(commons.getNumberofDnaseinChr16()+1);
			}else if(dnase.getChromName().equals("chr17")){
				commons.getChr17BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr17(commons.getNumberofDnaseinChr17()+1);
			}else if(dnase.getChromName().equals("chr18")){
				commons.getChr18BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr18(commons.getNumberofDnaseinChr18()+1);
			}else if(dnase.getChromName().equals("chr19")){
				commons.getChr19BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr19(commons.getNumberofDnaseinChr19()+1);
			}else if(dnase.getChromName().equals("chr20")){
				commons.getChr20BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr20(commons.getNumberofDnaseinChr20()+1);
			}else if(dnase.getChromName().equals("chr21")){
				commons.getChr21BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr21(commons.getNumberofDnaseinChr21()+1);
			}else if(dnase.getChromName().equals("chr22")){
				commons.getChr22BufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChr22(commons.getNumberofDnaseinChr22()+1);
			}else if(dnase.getChromName().equals("chrX")){
				commons.getChrXBufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChrX(commons.getNumberofDnaseinChrX()+1);
			}else if(dnase.getChromName().equals("chrY")){
				commons.getChrYBufferedWriter().write(dnase.getChromName() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+"\n");
				commons.setNumberofDnaseinChrY(commons.getNumberofDnaseinChrY()+1);
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


   }
	
   
 //Write the histone into corresponding chromosome file
	public void writetoUnsortedChrBaseHistoneFile(Histone histone){
		
		try {
			if(histone.getChromName().equals("chr1")){
				commons.getChr1BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr1(commons.getNumberofHistoneinChr1()+1);
			}else if (histone.getChromName().equals("chr2")){
				commons.getChr2BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr2(commons.getNumberofHistoneinChr2()+1);				
			}else if (histone.getChromName().equals("chr3")){
				commons.getChr3BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr3(commons.getNumberofHistoneinChr3()+1);				
			}else if (histone.getChromName().equals("chr4")){
				commons.getChr4BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr4(commons.getNumberofHistoneinChr4()+1);				
			}else if (histone.getChromName().equals("chr5")){
				commons.getChr5BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr5(commons.getNumberofHistoneinChr5()+1);				
			}else if (histone.getChromName().equals("chr6")){
				commons.getChr6BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr6(commons.getNumberofHistoneinChr6()+1);				
			}else if (histone.getChromName().equals("chr7")){
				commons.getChr7BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr7(commons.getNumberofHistoneinChr7()+1);				
			}else if (histone.getChromName().equals("chr8")){
				commons.getChr8BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr8(commons.getNumberofHistoneinChr8()+1);				
			}else if (histone.getChromName().equals("chr9")){
				commons.getChr9BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr9(commons.getNumberofHistoneinChr9()+1);				
			}else if (histone.getChromName().equals("chr10")){
				commons.getChr10BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr10(commons.getNumberofHistoneinChr10()+1);				
			}else if (histone.getChromName().equals("chr11")){
				commons.getChr11BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr11(commons.getNumberofHistoneinChr11()+1);				
			}else if (histone.getChromName().equals("chr12")){
				commons.getChr12BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr12(commons.getNumberofHistoneinChr12()+1);				
			}else if (histone.getChromName().equals("chr13")){
				commons.getChr13BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr13(commons.getNumberofHistoneinChr13()+1);				
			}else if (histone.getChromName().equals("chr14")){
				commons.getChr14BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr14(commons.getNumberofHistoneinChr14()+1);				
			}else if (histone.getChromName().equals("chr15")){
				commons.getChr15BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr15(commons.getNumberofHistoneinChr15()+1);				
			}else if (histone.getChromName().equals("chr16")){
				commons.getChr16BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr16(commons.getNumberofHistoneinChr16()+1);				
			}else if (histone.getChromName().equals("chr17")){
				commons.getChr17BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr17(commons.getNumberofHistoneinChr17()+1);				
			}else if (histone.getChromName().equals("chr18")){
				commons.getChr18BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr18(commons.getNumberofHistoneinChr18()+1);				
			}else if (histone.getChromName().equals("chr19")){
				commons.getChr19BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr19(commons.getNumberofHistoneinChr19()+1);				
			}else if (histone.getChromName().equals("chr20")){
				commons.getChr20BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr20(commons.getNumberofHistoneinChr20()+1);				
			}else if (histone.getChromName().equals("chr21")){
				commons.getChr21BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr21(commons.getNumberofHistoneinChr21()+1);				
			}else if (histone.getChromName().equals("chr22")){
				commons.getChr22BufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChr22(commons.getNumberofHistoneinChr22()+1);				
			}else if (histone.getChromName().equals("chrX")){
				commons.getChrXBufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChrX(commons.getNumberofHistoneinChrX()+1);				
			}else if (histone.getChromName().equals("chrY")){
				commons.getChrYBufferedWriter().write(histone.getChromName() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+"\n");
				commons.setNumberofHistoneinChrY(commons.getNumberofHistoneinChrY()+1);				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

   
		
	//Write the tfbs into thecorresponding chromosome file
	public void writetoUnsortedChrBaseTfbsFile(TranscriptionFactorBindingSite tfbs){
		
		try {
			if(tfbs.getChromName().equals("chr1")){
				commons.getChr1BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr1(commons.getNumberofTfbsinChr1()+1);
			}else if (tfbs.getChromName().equals("chr2")){
				commons.getChr2BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr2(commons.getNumberofTfbsinChr2()+1);
				
			}else if (tfbs.getChromName().equals("chr3")){
				commons.getChr3BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr3(commons.getNumberofTfbsinChr3()+1);

			}else if (tfbs.getChromName().equals("chr4")){
				commons.getChr4BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr4(commons.getNumberofTfbsinChr4()+1);

			}else if (tfbs.getChromName().equals("chr5")){
				commons.getChr5BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr5(commons.getNumberofTfbsinChr5()+1);

			}else if (tfbs.getChromName().equals("chr6")){
				commons.getChr6BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr6(commons.getNumberofTfbsinChr6()+1);
				
			}else if (tfbs.getChromName().equals("chr7")){
				commons.getChr7BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr7(commons.getNumberofTfbsinChr7()+1);

			}else if (tfbs.getChromName().equals("chr8")){
				commons.getChr8BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr8(commons.getNumberofTfbsinChr8()+1);
			
			}else if (tfbs.getChromName().equals("chr9")){
				commons.getChr9BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr9(commons.getNumberofTfbsinChr9()+1);

			}else if (tfbs.getChromName().equals("chr10")){
				commons.getChr10BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr10(commons.getNumberofTfbsinChr10()+1);

			}else if (tfbs.getChromName().equals("chr11")){
				commons.getChr11BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr11(commons.getNumberofTfbsinChr11()+1);

			}else if (tfbs.getChromName().equals("chr12")){
				commons.getChr12BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr12(commons.getNumberofTfbsinChr12()+1);

			}else if (tfbs.getChromName().equals("chr13")){
				commons.getChr13BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr13(commons.getNumberofTfbsinChr13()+1);
				
			}else if (tfbs.getChromName().equals("chr14")){
				commons.getChr14BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr14(commons.getNumberofTfbsinChr14()+1);

			}else if (tfbs.getChromName().equals("chr15")){
				commons.getChr15BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr15(commons.getNumberofTfbsinChr15()+1);

			}else if (tfbs.getChromName().equals("chr16")){
				commons.getChr16BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr16(commons.getNumberofTfbsinChr16()+1);

			}else if (tfbs.getChromName().equals("chr17")){
				commons.getChr17BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr17(commons.getNumberofTfbsinChr17()+1);

			}else if (tfbs.getChromName().equals("chr18")){
				commons.getChr18BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr18(commons.getNumberofTfbsinChr18()+1);

			}else if (tfbs.getChromName().equals("chr19")){
				commons.getChr19BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr19(commons.getNumberofTfbsinChr19()+1);

			}else if (tfbs.getChromName().equals("chr20")){
				commons.getChr20BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr20(commons.getNumberofTfbsinChr20()+1);

			}else if (tfbs.getChromName().equals("chr21")){
				commons.getChr21BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr21(commons.getNumberofTfbsinChr21()+1);

			}else if (tfbs.getChromName().equals("chr22")){
				commons.getChr22BufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChr22(commons.getNumberofTfbsinChr22()+1);

			}else if (tfbs.getChromName().equals("chrX")){
				commons.getChrXBufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChrX(commons.getNumberofTfbsinChrX()+1);

			}else if (tfbs.getChromName().equals("chrY")){
				commons.getChrYBufferedWriter().write(tfbs.getChromName() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+"\n");
				commons.setNumberofTfbsinChrY(commons.getNumberofTfbsinChrY()+1);

			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	Get Dnase data from strLine and cellLineDnase and fill dnase
	public void getDnaseData(String strLine, CellLineDnase cellLineDnase, Dnase dnase){
//		get these data from cellLineDnase
//		In dnase there is no dnase name 
		dnase.setCellLineName(cellLineDnase.getCellLineName());
		dnase.setFileName(cellLineDnase.getFileName());
		
//		get these data from strLine		
		int indexofFirstTab =0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		int indexofSixthTab=0;
		
		int start =0;
		int end = 0;
		
		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
		indexofFourthTab= strLine.indexOf('\t',indexofThirdTab+1);
		indexofFifthTab= strLine.indexOf('\t',indexofFourthTab+1);
		indexofSixthTab= strLine.indexOf('\t',indexofFifthTab+1);
		
		start = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
		end = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
		
		//Encode data 
		//start 0-based inclusive
		//end exclusive
		
		//my convention is
		//start 0-based inclusive
		//end 0-based inclusive
		
		dnase.setChromName(strLine.substring(0, indexofFirstTab));
		dnase.setStartPos(start);
		dnase.setEndPos(end-1);	
		
		// +/- or . if no orientation is assigned.
		dnase.setStrand(strLine.substring(indexofFifthTab+1, indexofSixthTab).charAt(0));
		
		
	}
	
	
//	Get Histone data from strLine and cellLineNameHistoneName and fill histone
		public void getHistoneData(String strLine, CellLineHistone cellLineNameHistoneName, Histone histone){
		//get these data from cellLineNameHistoneName
		histone.setHistoneName(cellLineNameHistoneName.getHistoneName());
		histone.setCellLineName(cellLineNameHistoneName.getCellLineName());
		histone.setFileName(cellLineNameHistoneName.getFileName());
				
//		get these data from strLine		
		int indexofFirstTab =0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		int indexofSixthTab=0;
		
		int start =0;
		int end =0;
		
		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
		indexofFourthTab= strLine.indexOf('\t',indexofThirdTab+1);
		indexofFifthTab= strLine.indexOf('\t',indexofFourthTab+1);
		indexofSixthTab= strLine.indexOf('\t',indexofFifthTab+1);
		
	
		start = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
		end = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
			
		//Encode data 
		//start 0-based inclusive
		//end exclusive
		
		//my convention is
		//start 0-based inclusive
		//end 0-based inclusive

	
		histone.setChromName(strLine.substring(0, indexofFirstTab));
		histone.setStartPos(start);
		histone.setEndPos(end-1);
		
		// +/- or . if no orientation is assigned.
		histone.setStrand(strLine.substring(indexofFifthTab+1, indexofSixthTab).charAt(0));
			
		
			
	}
	
	
	//Get Tfbs data from strLine and cellLineTranscriptionFactor then fill tfbs
	// Get chrName, transcriptionFactorBindingSite start position and end position
	
	public void getTranscriptionFactorBindingSiteData(String strLine, CellLineTranscriptionFactor cellLineTranscriptionFactor, TranscriptionFactorBindingSite tfbs){
		// get these data from cellLineTranscriptionFactor
		tfbs.setTranscriptionFactorName(cellLineTranscriptionFactor.getTranscriptionFactorName());
		tfbs.setCellLineName(cellLineTranscriptionFactor.getCellLineName());
		tfbs.setFileName(cellLineTranscriptionFactor.getFileName());
				
//		get these data from strLine
		
		int indexofFirstTab =0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		int indexofSixthTab=0;
		
		int start =0;
		int end =0;
	
		
		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
		indexofFourthTab= strLine.indexOf('\t',indexofThirdTab+1);
		indexofFifthTab= strLine.indexOf('\t',indexofFourthTab+1);
		indexofSixthTab= strLine.indexOf('\t',indexofFifthTab+1);
		
		start = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
		end = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
	
		//Encode data 
		//start 0-based inclusive
		//end exclusive
		
		//my convention is
		//start 0-based inclusive
		//end 0-based inclusive
	
		tfbs.setChromName(strLine.substring(0, indexofFirstTab));
		tfbs.setStartPos(start);
		tfbs.setEndPos(end-1);
		
		// +/- or . if no orientation is assigned.
		tfbs.setStrand(strLine.substring(indexofFifthTab+1, indexofSixthTab).charAt(0));

		
		
		
	}
	
	public static void getCellLineName(CellLineDnase cellLineDnase, String fileName){
//		example fileName
//		It seems that there are five different kinds of labs 
//SM_SFMDukeDNaseSeq.pk
//hESCT0-DS13133.peaks.fdr0.01.hg19.bed
//idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak
//idrPool.GM12878_FAIRE_BP_TP_peaks_OV_GM12878_B1_peaks_VS_UncFAIRE_GM12878_B2_peaks.npk2.narrowPeak
//idrPool.GM12878_DNaseHS_BP_TP_P_peaks_OV_DukeDNase_GM12878_B4_peaks_VS_DukeDNase_GM12878_B5_peaks.npk2.narrowPeak		
		
		int start = 0;
		int indexofDukeDnase = fileName.indexOf("DukeDNase");
		int indexof_DS = fileName.indexOf("-DS");
		int indexof_Dnase = fileName.indexOf("_DNase");
		int indexof_FAIRE = fileName.indexOf("_FAIRE");
		int indexofIdrPool = fileName.indexOf("idrPool");
		
		if (indexofDukeDnase >=0){
			
			if (indexof_Dnase>=0){
				if (indexofIdrPool>=0){
					start = start + "idrPool.".length();
					cellLineDnase.setCellLineName(fileName.substring(start, indexof_Dnase).toUpperCase(Locale.ENGLISH));																
				}else{
					System.out.println("Unknown Lab: " + fileName);
				}
			} else{			
				cellLineDnase.setCellLineName(fileName.substring(start, indexofDukeDnase).toUpperCase(Locale.ENGLISH));
			}
		}else if (indexof_DS>=0){
			if (indexofIdrPool < 0){
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_DS).toUpperCase(Locale.ENGLISH));			
			} else if (indexofIdrPool>=0){
				start = start + "idrPool.".length();
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_DS).toUpperCase(Locale.ENGLISH));								
			}else{
				System.out.println("Unknown Lab: " + fileName);
			}
		}else if (indexof_Dnase>=0){
			if (indexofIdrPool>=0){
				start = start + "idrPool.".length();
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_Dnase).toUpperCase(Locale.ENGLISH));																
			}else{
				System.out.println("Unknown Lab: " + fileName);
			}
		}else if (indexof_FAIRE>=0){
			if (indexofIdrPool>=0){
				start = start + "idrPool.".length();
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_FAIRE).toUpperCase(Locale.ENGLISH));																				
			}else{
				System.out.println("Unknown Lab: " + fileName);
			}
			
		}else{
			System.out.println("Unknown Lab: " + fileName);
		}
		
		cellLineDnase.setFileName(fileName);
	}
	

	public static void getCellLineNameandHistoneName(CellLineHistone cellLineHistone, String fileName) {
//		example fileName
//		It seems that there are three different kinds of labs 
//		wgEncodeUwHistoneNhekH3k4me3StdAln.narrowPeak
//		wgEncodeSydhHistoneU2osH3k9me3UcdAln.narrowPeak
//		wgEncodeBroadHistoneOsteoblH3k9me3StdAln.narrowPeak
		
		int uwHistoneStart = fileName.indexOf("UwHistone");
		int sydhHistoneStart = fileName.indexOf("SydhHistone");
		int broadHistoneStart = fileName.indexOf("BroadHistone");		
		
		int start =0;
		int cellLineNameStart = 0;
		int histoneNameStart = 0;
		int laborProtocolNameStart =0;
		
		if (uwHistoneStart >=0){
			start = uwHistoneStart+"UwHistone".length();
		} else if (sydhHistoneStart>=0){
			start = sydhHistoneStart + "SydhHistone".length();
		}else if (broadHistoneStart>=0){
			start = broadHistoneStart+ "BroadHistone".length();
		}else{
			System.out.println("Unknown Lab in  Histone Files");	
		}
		
		
//		Find the First,  Second and Third Upper Case Letters in filename after the lab name		
		for (int i = start; i<fileName.length(); i++){
			if (Character.isUpperCase(fileName.charAt(i))){
				cellLineNameStart = i;
				break;
			}				
		}
		
		for (int i = cellLineNameStart+1; i<fileName.length(); i++){
			if (Character.isUpperCase(fileName.charAt(i))){
				histoneNameStart = i;
				break;
			}				
		}
		
		for (int i = histoneNameStart+1; i<fileName.length(); i++){
			if (Character.isUpperCase(fileName.charAt(i))){
				laborProtocolNameStart = i;
				break;
			}								
		}
		
		cellLineHistone.setCellLineName(fileName.substring(cellLineNameStart, histoneNameStart).toUpperCase(Locale.ENGLISH));
		cellLineHistone.setHistoneName(fileName.substring(histoneNameStart,laborProtocolNameStart).toUpperCase(Locale.ENGLISH));
		cellLineHistone.setFileName(fileName);		
		
	}
	
	
	
	
	
	public static void getCellLineNameandTranscriptionFactorName(CellLineTranscriptionFactor cellLineandTranscriptionFactor ,String fileName){
//		example fileName
//		It seems that there are three kind of fileName classes
//		spp.optimal.wgEncodeBroadHistoneGm12878CtcfStdAlnRep0_VS_wgEncodeBroadHistoneGm12878ControlStdAlnRep0.narrowPeak
//		spp.optimal.wgEncodeOpenChromChipFibroblCtcfAlnRep0_VS_wgEncodeOpenChromChipFibroblInputAln.narrowPeak
//		spp.optimal.wgEncodeSydhTfbsK562Pol2IggmusAlnRep0_VS_wgEncodeSydhTfbsK562InputIggmusAlnRep0.narrowPeak
		
		int tfbsStart = fileName.indexOf("Tfbs");		
		int openChromChipStart = fileName.indexOf("OpenChromChip");
		int broadHistoneStart = fileName.indexOf("BroadHistone");
		
		int start = 0;
		
		int cellLineNameStart = 0;
		int transcriptionFactorNameStart = 0;
		int laborProtocolNameStart = 0;
		
		if (tfbsStart >= 0){
//			Tfbs is 4 char long
			start = tfbsStart+"Tfbs".length();
		} else if (openChromChipStart>=0){
//			OpenChromChip
			start = openChromChipStart+ "OpenChromChip".length();			
		}else if (broadHistoneStart >=0){
//			BroadHistone
			start = broadHistoneStart+"BroadHistone".length();			
		}else{
			System.out.println("Unknown Lab in TFBS file");	
		}
			
		
	
//			Find the First,  Second and Third Upper Case Letters in filename after the lab name
			
			for (int i = start; i<fileName.length(); i++){
				if (Character.isUpperCase(fileName.charAt(i))){
					cellLineNameStart = i;
					break;
				}				
			}
			
			for (int i = cellLineNameStart+1; i<fileName.length(); i++){
				if (Character.isUpperCase(fileName.charAt(i))){
					transcriptionFactorNameStart = i;
					break;
				}				
			}
			
			for (int i = transcriptionFactorNameStart+1; i<fileName.length(); i++){
				if (Character.isUpperCase(fileName.charAt(i))){
					laborProtocolNameStart = i;
					break;
				}								
			}
			
			cellLineandTranscriptionFactor.setCellLineName(fileName.substring(cellLineNameStart, transcriptionFactorNameStart).toUpperCase(Locale.ENGLISH));
			cellLineandTranscriptionFactor.setTranscriptionFactorName(fileName.substring(transcriptionFactorNameStart,laborProtocolNameStart).toUpperCase(Locale.ENGLISH));
			cellLineandTranscriptionFactor.setFileName(fileName);
			
	}
	
	
	public void readEncodeDnaseFilesandWriteUnsortedChromBaseDnaseFiles(File directory){
//		Use same cellLineDnase object for each file
		CellLineDnase cellLineDnase = new CellLineDnase();
//		Use the same Dnase object for each read line
		Dnase dnase = new Dnase();
		
		long numberofIntervalsInDnaseFiles = 0;
		
		if(!directory.exists()){
			 System.out.println("No File/Dir" + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    File[] files = directory.listFiles();
			    int numberofDnaseFiles= files.length;
			    
			    System.out.printf("Number of Dnase Files %d in %s\n", files.length, directory.getAbsolutePath());
				
		        for (int i = 0; i < numberofDnaseFiles; i++) {
		        	FileReader fileReader =null;
		        	BufferedReader br = null;
		        	
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		File file = files[i];		        		
		        		
		        		String fileName = file.getName();		
		    			String filePath = file.getPath();
		    			
		    			 // Open the file that is the first 		    			  		    			  
		    			try {
		    				    fileReader = new FileReader(filePath);
		    					br = new BufferedReader(fileReader);
		    					
		    					String strLine;
		    					
//				        		Get the cell line name from file name
		    					getCellLineName(cellLineDnase,fileName);
		    				  
		    					try {
		    						while ((strLine = br.readLine()) != null)   {
		    							getDnaseData(strLine, cellLineDnase, dnase);
		    												
		    							writetoUnsortedChrBaseDnaseFile(dnase);
		    							numberofIntervalsInDnaseFiles++;
		    						}
		    					} catch (IOException e) {
		    						// TODO Auto-generated catch block
		    						e.printStackTrace();
		    					}
		    						
//		    			Close the Buffered Reader
		    					br.close();
		    					
		    			} catch (FileNotFoundException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			 
		        	}//Check for each file and read each file		        	
//		            System.out.println(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
				System.out.println("number of Intervals In Dnase Files:  " + numberofIntervalsInDnaseFiles);
    			
	        } //End of if: For all files in this directory
		
	}
	
	
	
	public void readEncodeHistoneFilesandWriteUnsortedChromBaseHistoneFiles(File mainDirectory){
//		Use same cellLineHistone object for each file
		CellLineHistone cellLineNameHistoneName  = new CellLineHistone();
//		Use the same histone object for each read line
		Histone histone = new Histone();
		
		long numberofIntervalsInHistoneFiles = 0;
		
		
		 if(!mainDirectory.exists()){
			 System.out.println("No File/Dir"); 
		 }
        
		 // Reading directory contents
		 if(mainDirectory.isDirectory()){// a directory!
			 
			    File[] files = mainDirectory.listFiles();
			    int numberofHistoneFiles= files.length;
			    
			    System.out.printf("Number of Histone Files %d in %s\n", files.length,mainDirectory.getAbsolutePath());
			    
		        for (int i = 0; i < numberofHistoneFiles; i++) {
		        	FileReader fileReader =null;
		        	BufferedReader br = null;
		        	
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		File file = files[i];		        		
		        		
		        		String fileName = file.getName();		
		    			String filePath = file.getPath();
		    			
		    			 // Open the file that is the first 		    			  		    			  
		    			try {
		    				    fileReader = new FileReader(filePath);
		    					br = new BufferedReader(fileReader);
		    					
		    					String strLine;
		    					
//				        		Get the cell line name and histone name from file name
		    					getCellLineNameandHistoneName(cellLineNameHistoneName,fileName);
		    				  
		    					try {
		    						while ((strLine = br.readLine()) != null)   {
		    							getHistoneData(strLine, cellLineNameHistoneName, histone);
		    							writetoUnsortedChrBaseHistoneFile(histone);
		    							numberofIntervalsInHistoneFiles++;
		    						}
		    					} catch (IOException e) {
		    						// TODO Auto-generated catch block
		    						e.printStackTrace();
		    					}
		    						
//		    				close the Buffered Reader
//		    			The typically use is to close the outermost reader in the chain		
		    					br.close();
		    					
		    						
		    			} catch (FileNotFoundException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			 

		        	}//Check for each file and read each file
		        	
//		            System.out.println(files[i]);		            
		         
		           		            
		        }	// End of For -----reading each file in the directory
	        	
				System.out.println("number of Intervals In Histone Files: " + numberofIntervalsInHistoneFiles);
	    		
	        } //End of if: For all files in this directory
				
	}	
	
	public void readEncodeTfbsFilesandWriteUnsortedChromBaseTfbsFiles(File mainDirectory){
//		Use same CellLineTranscriptionFactor object for each file
		CellLineTranscriptionFactor cellLineandTranscriptionFactorName  = new CellLineTranscriptionFactor();
//		Use the same transcription factor binding site object for each read line
		TranscriptionFactorBindingSite tfbs = new TranscriptionFactorBindingSite();
		
		int numberofIntervalsInTranscriptionFactorFiles = 0;
		
		 if(!mainDirectory.exists()){
			 System.out.println("No File/Dir"); 
		 }
        
		 // Reading directory contents
		 if(mainDirectory.isDirectory()){// a directory!
			 
			    File[] files = mainDirectory.listFiles();
			    int numberofTfbsFiles= files.length;
			    
			    System.out.printf("Number of Tfbs Files %d in %s\n", files.length, mainDirectory.getAbsolutePath());
			       
		        for (int i = 0; i < numberofTfbsFiles; i++) {
		        	FileReader fileReader =null;
		        	BufferedReader br = null;
		        	
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		File file = files[i];		        		
		        		
		        		String fileName = file.getName();		
		    			String filePath = file.getPath();
		    			
		    			 // Open the file that is the first 		    			  		    			  
		    			try {
		    				    fileReader = new FileReader(filePath);
		    					br = new BufferedReader(fileReader);
		    					
		    					String strLine;
		    					
//				        		Get the cell line name and transcription factor name from file name
		    					getCellLineNameandTranscriptionFactorName(cellLineandTranscriptionFactorName,fileName);
		    				  
		    					try {
		    						while ((strLine = br.readLine()) != null)   {
		    							getTranscriptionFactorBindingSiteData(strLine, cellLineandTranscriptionFactorName, tfbs);
		    							writetoUnsortedChrBaseTfbsFile(tfbs);
		    							numberofIntervalsInTranscriptionFactorFiles++;
		    						}
		    					} catch (IOException e) {
		    						// TODO Auto-generated catch block
		    						e.printStackTrace();
		    					}
		    						
//		    				close the Buffered Reader
//		    			The typically use is to close the outermost reader in the chain		
		    					br.close();
		    					
		    					
		    			} catch (FileNotFoundException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			 

		        	}//Check for each file and read each file
		        	
//		            System.out.println(files[i]);		            
		         
		           		            
		        }	// End of For -----reading each file in the directory
	        	
		        
				System.out.println("number of Intervals In Transcription Factor Files: " + numberofIntervalsInTranscriptionFactorFiles);
			    
	        } //For all files in this directory
				
	}
	
	
//	Open Chromosome base Dnase Files 
	public void openUnsortedChromosomeBaseDnaseFiles(){
		try {
			commons.setChr1FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_DNASE));
			commons.setChr2FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_DNASE));
			commons.setChr3FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_DNASE));
			commons.setChr4FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_DNASE));
			commons.setChr5FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_DNASE));
			commons.setChr6FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_DNASE));
			commons.setChr7FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_DNASE));
			commons.setChr8FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_DNASE));
			commons.setChr9FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_DNASE));
			commons.setChr10FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_DNASE));
			commons.setChr11FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_DNASE));
			commons.setChr12FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_DNASE));
			commons.setChr13FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_DNASE));
			commons.setChr14FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_DNASE));
			commons.setChr15FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_DNASE));
			commons.setChr16FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_DNASE));
			commons.setChr17FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_DNASE));
			commons.setChr18FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_DNASE));
			commons.setChr19FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_DNASE));
			commons.setChr20FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_DNASE));
			commons.setChr21FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_DNASE));
			commons.setChr22FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_DNASE));
			commons.setChrXFileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_DNASE));
			commons.setChrYFileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_DNASE));				
					 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 commons.setChr1BufferedWriter(new BufferedWriter(commons.getChr1FileWriter()));
	 commons.setChr2BufferedWriter(new BufferedWriter(commons.getChr2FileWriter()));
	 commons.setChr3BufferedWriter(new BufferedWriter(commons.getChr3FileWriter()));
	 commons.setChr4BufferedWriter(new BufferedWriter(commons.getChr4FileWriter()));
	 commons.setChr5BufferedWriter(new BufferedWriter(commons.getChr5FileWriter()));
	 commons.setChr6BufferedWriter(new BufferedWriter(commons.getChr6FileWriter()));
	 commons.setChr7BufferedWriter(new BufferedWriter(commons.getChr7FileWriter()));
	 commons.setChr8BufferedWriter(new BufferedWriter(commons.getChr8FileWriter()));
	 commons.setChr9BufferedWriter(new BufferedWriter(commons.getChr9FileWriter()));
	 commons.setChr10BufferedWriter(new BufferedWriter(commons.getChr10FileWriter()));
	 commons.setChr11BufferedWriter(new BufferedWriter(commons.getChr11FileWriter()));
	 commons.setChr12BufferedWriter(new BufferedWriter(commons.getChr12FileWriter()));
	 commons.setChr13BufferedWriter(new BufferedWriter(commons.getChr13FileWriter()));
	 commons.setChr14BufferedWriter(new BufferedWriter(commons.getChr14FileWriter()));
	 commons.setChr15BufferedWriter(new BufferedWriter(commons.getChr15FileWriter()));
	 commons.setChr16BufferedWriter(new BufferedWriter(commons.getChr16FileWriter()));
	 commons.setChr17BufferedWriter(new BufferedWriter(commons.getChr17FileWriter()));
	 commons.setChr18BufferedWriter(new BufferedWriter(commons.getChr18FileWriter()));
	 commons.setChr19BufferedWriter(new BufferedWriter(commons.getChr19FileWriter()));
	 commons.setChr20BufferedWriter(new BufferedWriter(commons.getChr20FileWriter()));
	 commons.setChr21BufferedWriter(new BufferedWriter(commons.getChr21FileWriter()));
	 commons.setChr22BufferedWriter(new BufferedWriter(commons.getChr22FileWriter()));
	 commons.setChrXBufferedWriter(new BufferedWriter(commons.getChrXFileWriter()));
	 commons.setChrYBufferedWriter(new BufferedWriter(commons.getChrYFileWriter()));		 				

	}

	
//	Open Chromosome base Histone Mark Files 
	public void openUnsortedChromosomeBaseHistoneFiles(){
		try {
			commons.setChr1FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_HISTONE));
			commons.setChr2FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_HISTONE));
			commons.setChr3FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_HISTONE));
			commons.setChr4FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_HISTONE));
			commons.setChr5FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_HISTONE));
			commons.setChr6FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_HISTONE));
			commons.setChr7FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_HISTONE));
			commons.setChr8FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_HISTONE));
			commons.setChr9FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_HISTONE));
			commons.setChr10FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_HISTONE));
			commons.setChr11FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_HISTONE));
			commons.setChr12FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_HISTONE));
			commons.setChr13FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_HISTONE));
			commons.setChr14FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_HISTONE));
			commons.setChr15FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_HISTONE));
			commons.setChr16FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_HISTONE));
			commons.setChr17FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_HISTONE));
			commons.setChr18FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_HISTONE));
			commons.setChr19FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_HISTONE));
			commons.setChr20FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_HISTONE));
			commons.setChr21FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_HISTONE));
			commons.setChr22FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_HISTONE));
			commons.setChrXFileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_HISTONE));
			commons.setChrYFileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_HISTONE));				
					 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 commons.setChr1BufferedWriter(new BufferedWriter(commons.getChr1FileWriter()));
	 commons.setChr2BufferedWriter(new BufferedWriter(commons.getChr2FileWriter()));
	 commons.setChr3BufferedWriter(new BufferedWriter(commons.getChr3FileWriter()));
	 commons.setChr4BufferedWriter(new BufferedWriter(commons.getChr4FileWriter()));
	 commons.setChr5BufferedWriter(new BufferedWriter(commons.getChr5FileWriter()));
	 commons.setChr6BufferedWriter(new BufferedWriter(commons.getChr6FileWriter()));
	 commons.setChr7BufferedWriter(new BufferedWriter(commons.getChr7FileWriter()));
	 commons.setChr8BufferedWriter(new BufferedWriter(commons.getChr8FileWriter()));
	 commons.setChr9BufferedWriter(new BufferedWriter(commons.getChr9FileWriter()));
	 commons.setChr10BufferedWriter(new BufferedWriter(commons.getChr10FileWriter()));
	 commons.setChr11BufferedWriter(new BufferedWriter(commons.getChr11FileWriter()));
	 commons.setChr12BufferedWriter(new BufferedWriter(commons.getChr12FileWriter()));
	 commons.setChr13BufferedWriter(new BufferedWriter(commons.getChr13FileWriter()));
	 commons.setChr14BufferedWriter(new BufferedWriter(commons.getChr14FileWriter()));
	 commons.setChr15BufferedWriter(new BufferedWriter(commons.getChr15FileWriter()));
	 commons.setChr16BufferedWriter(new BufferedWriter(commons.getChr16FileWriter()));
	 commons.setChr17BufferedWriter(new BufferedWriter(commons.getChr17FileWriter()));
	 commons.setChr18BufferedWriter(new BufferedWriter(commons.getChr18FileWriter()));
	 commons.setChr19BufferedWriter(new BufferedWriter(commons.getChr19FileWriter()));
	 commons.setChr20BufferedWriter(new BufferedWriter(commons.getChr20FileWriter()));
	 commons.setChr21BufferedWriter(new BufferedWriter(commons.getChr21FileWriter()));
	 commons.setChr22BufferedWriter(new BufferedWriter(commons.getChr22FileWriter()));
	 commons.setChrXBufferedWriter(new BufferedWriter(commons.getChrXFileWriter()));
	 commons.setChrYBufferedWriter(new BufferedWriter(commons.getChrYFileWriter()));		 				

	}
	
//	open Chromosome base Tfbs files
//	Each Chromosome base Tfbs file contains transcription factor binding sites information
	public void openUnsortedChromosomeBaseTfbsFiles(){
						
		try {
				commons.setChr1FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_TFBS));
				commons.setChr2FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_TFBS));
				commons.setChr3FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_TFBS));
				commons.setChr4FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_TFBS));
				commons.setChr5FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_TFBS));
				commons.setChr6FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_TFBS));
				commons.setChr7FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_TFBS));
				commons.setChr8FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_TFBS));
				commons.setChr9FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_TFBS));
				commons.setChr10FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_TFBS));
				commons.setChr11FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_TFBS));
				commons.setChr12FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_TFBS));
				commons.setChr13FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_TFBS));
				commons.setChr14FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_TFBS));
				commons.setChr15FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_TFBS));
				commons.setChr16FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_TFBS));
				commons.setChr17FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_TFBS));
				commons.setChr18FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_TFBS));
				commons.setChr19FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_TFBS));
				commons.setChr20FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_TFBS));
				commons.setChr21FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_TFBS));
				commons.setChr22FileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_TFBS));
				commons.setChrXFileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_TFBS));
				commons.setChrYFileWriter(new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_TFBS));				
						 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 commons.setChr1BufferedWriter(new BufferedWriter(commons.getChr1FileWriter()));
		 commons.setChr2BufferedWriter(new BufferedWriter(commons.getChr2FileWriter()));
		 commons.setChr3BufferedWriter(new BufferedWriter(commons.getChr3FileWriter()));
		 commons.setChr4BufferedWriter(new BufferedWriter(commons.getChr4FileWriter()));
		 commons.setChr5BufferedWriter(new BufferedWriter(commons.getChr5FileWriter()));
		 commons.setChr6BufferedWriter(new BufferedWriter(commons.getChr6FileWriter()));
		 commons.setChr7BufferedWriter(new BufferedWriter(commons.getChr7FileWriter()));
		 commons.setChr8BufferedWriter(new BufferedWriter(commons.getChr8FileWriter()));
		 commons.setChr9BufferedWriter(new BufferedWriter(commons.getChr9FileWriter()));
		 commons.setChr10BufferedWriter(new BufferedWriter(commons.getChr10FileWriter()));
		 commons.setChr11BufferedWriter(new BufferedWriter(commons.getChr11FileWriter()));
		 commons.setChr12BufferedWriter(new BufferedWriter(commons.getChr12FileWriter()));
		 commons.setChr13BufferedWriter(new BufferedWriter(commons.getChr13FileWriter()));
		 commons.setChr14BufferedWriter(new BufferedWriter(commons.getChr14FileWriter()));
		 commons.setChr15BufferedWriter(new BufferedWriter(commons.getChr15FileWriter()));
		 commons.setChr16BufferedWriter(new BufferedWriter(commons.getChr16FileWriter()));
		 commons.setChr17BufferedWriter(new BufferedWriter(commons.getChr17FileWriter()));
		 commons.setChr18BufferedWriter(new BufferedWriter(commons.getChr18FileWriter()));
		 commons.setChr19BufferedWriter(new BufferedWriter(commons.getChr19FileWriter()));
		 commons.setChr20BufferedWriter(new BufferedWriter(commons.getChr20FileWriter()));
		 commons.setChr21BufferedWriter(new BufferedWriter(commons.getChr21FileWriter()));
		 commons.setChr22BufferedWriter(new BufferedWriter(commons.getChr22FileWriter()));
		 commons.setChrXBufferedWriter(new BufferedWriter(commons.getChrXFileWriter()));
		 commons.setChrYBufferedWriter(new BufferedWriter(commons.getChrYFileWriter()));		 						 		 
	}
	
//	Common function for tfbs, histone and dnase
	public void closeUnsortedChromosomeBaseFiles() {
		try {
//			Close the outermost Stream/Writer/Reader in the chain.
//			Which is BufferedWriter in this case
//			It will flush it buffers, close and propagate close to the next Writer in the chain.
//			There is no need to close the FileWriter explicitly
//			But if we want to close the FileWriter explicitly
//			First we should close the BufferedWriter
//			Then we should close the FileWriter
//			Otherwise we will lose the remaining chars in the buffers of BufferedWriter

			commons.getChr1BufferedWriter().close();
			commons.getChr2BufferedWriter().close();
			commons.getChr3BufferedWriter().close();
			commons.getChr4BufferedWriter().close();
			commons.getChr5BufferedWriter().close();
			commons.getChr6BufferedWriter().close();
			commons.getChr7BufferedWriter().close();
			commons.getChr8BufferedWriter().close();
			commons.getChr9BufferedWriter().close();
			commons.getChr10BufferedWriter().close();
			commons.getChr11BufferedWriter().close();
			commons.getChr12BufferedWriter().close();
			commons.getChr13BufferedWriter().close();
			commons.getChr14BufferedWriter().close();
			commons.getChr15BufferedWriter().close();
			commons.getChr16BufferedWriter().close();
			commons.getChr17BufferedWriter().close();
			commons.getChr18BufferedWriter().close();
			commons.getChr19BufferedWriter().close();
			commons.getChr20BufferedWriter().close();
			commons.getChr21BufferedWriter().close();
			commons.getChr22BufferedWriter().close();
			commons.getChrXBufferedWriter().close();
			commons.getChrYBufferedWriter().close();
			
//			After you close the BufferedReder (it flushes it buffers and then close and close propagate) you can also close FileWriter explicitly
//			no problem, but also no  need
//			commons.getChr10TfbsFw().close();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
//	Write Tfbs Information to the console
	public void writeTfbsInformationtoConsole(){
		
		System.out.print("Number of tfbs in Chr 1 " + commons.getNumberofTfbsinChr1()+ "\n");
		System.out.print("Number of tfbs in Chr 2 " + commons.getNumberofTfbsinChr2()+ "\n");
		System.out.print("Number of tfbs in Chr 3 " + commons.getNumberofTfbsinChr3()+ "\n");
		System.out.print("Number of tfbs in Chr 4 " + commons.getNumberofTfbsinChr4()+ "\n");
		System.out.print("Number of tfbs in Chr 5 " + commons.getNumberofTfbsinChr5()+ "\n");
		System.out.print("Number of tfbs in Chr 6 " + commons.getNumberofTfbsinChr6()+ "\n");
		System.out.print("Number of tfbs in Chr 7 " + commons.getNumberofTfbsinChr7()+ "\n");
		System.out.print("Number of tfbs in Chr 8 " + commons.getNumberofTfbsinChr8()+ "\n");
		System.out.print("Number of tfbs in Chr 9 " + commons.getNumberofTfbsinChr9()+ "\n");
		System.out.print("Number of tfbs in Chr 10 " + commons.getNumberofTfbsinChr10()+ "\n");
		System.out.print("Number of tfbs in Chr 11 " + commons.getNumberofTfbsinChr11()+ "\n");
		System.out.print("Number of tfbs in Chr 12 " + commons.getNumberofTfbsinChr12()+ "\n");
		System.out.print("Number of tfbs in Chr 13 " + commons.getNumberofTfbsinChr13()+ "\n");
		System.out.print("Number of tfbs in Chr 14 " + commons.getNumberofTfbsinChr14()+ "\n");
		System.out.print("Number of tfbs in Chr 15 " + commons.getNumberofTfbsinChr15()+ "\n");
		System.out.print("Number of tfbs in Chr 16 " + commons.getNumberofTfbsinChr16()+ "\n");
		System.out.print("Number of tfbs in Chr 17 " + commons.getNumberofTfbsinChr17()+ "\n");
		System.out.print("Number of tfbs in Chr 18 " + commons.getNumberofTfbsinChr18()+ "\n");
		System.out.print("Number of tfbs in Chr 19 " + commons.getNumberofTfbsinChr19()+ "\n");
		System.out.print("Number of tfbs in Chr 20 " + commons.getNumberofTfbsinChr20()+ "\n");
		System.out.print("Number of tfbs in Chr 21 " + commons.getNumberofTfbsinChr21()+ "\n");
		System.out.print("Number of tfbs in Chr 22 " + commons.getNumberofTfbsinChr22()+ "\n");
		System.out.print("Number of tfbs in Chr X " + commons.getNumberofTfbsinChrX()+ "\n");
		System.out.print("Number of tfbs in Chr Y " + commons.getNumberofTfbsinChrY()+ "\n");
	
		System.out.print("Total number of tfbs intervals " + commons.getTotalNumberofTfbs()+ "\n");
			
	}


//	Write Dnase Information to the console
	public void writeDnaseInformationtoConsole(){

		System.out.print("Number of dnase in Chr 1 " + commons.getNumberofDnaseinChr1()+ "\n");
		System.out.print("Number of dnase in Chr 2 " + commons.getNumberofDnaseinChr2()+ "\n");
		System.out.print("Number of dnase in Chr 3 " + commons.getNumberofDnaseinChr3()+ "\n");
		System.out.print("Number of dnase in Chr 4 " + commons.getNumberofDnaseinChr4()+ "\n");
		System.out.print("Number of dnase in Chr 5 " + commons.getNumberofDnaseinChr5()+ "\n");
		System.out.print("Number of dnase in Chr 6 " + commons.getNumberofDnaseinChr6()+ "\n");
		System.out.print("Number of dnase in Chr 7 " + commons.getNumberofDnaseinChr7()+ "\n");
		System.out.print("Number of dnase in Chr 8 " + commons.getNumberofDnaseinChr8()+ "\n");
		System.out.print("Number of dnase in Chr 9 " + commons.getNumberofDnaseinChr9()+ "\n");
		System.out.print("Number of dnase in Chr 10 " + commons.getNumberofDnaseinChr10()+ "\n");
		System.out.print("Number of dnase in Chr 11 " + commons.getNumberofDnaseinChr11()+ "\n");
		System.out.print("Number of dnase in Chr 12 " + commons.getNumberofDnaseinChr12()+ "\n");
		System.out.print("Number of dnase in Chr 13 " + commons.getNumberofDnaseinChr13()+ "\n");
		System.out.print("Number of dnase in Chr 14 " + commons.getNumberofDnaseinChr14()+ "\n");
		System.out.print("Number of dnase in Chr 15 " + commons.getNumberofDnaseinChr15()+ "\n");
		System.out.print("Number of dnase in Chr 16 " + commons.getNumberofDnaseinChr16()+ "\n");
		System.out.print("Number of dnase in Chr 17 " + commons.getNumberofDnaseinChr17()+ "\n");
		System.out.print("Number of dnase in Chr 18 " + commons.getNumberofDnaseinChr18()+ "\n");
		System.out.print("Number of dnase in Chr 19 " + commons.getNumberofDnaseinChr19()+ "\n");
		System.out.print("Number of dnase in Chr 20 " + commons.getNumberofDnaseinChr20()+ "\n");
		System.out.print("Number of dnase in Chr 21 " + commons.getNumberofDnaseinChr21()+ "\n");
		System.out.print("Number of dnase in Chr 22 " + commons.getNumberofDnaseinChr22()+ "\n");
		System.out.print("Number of dnase in Chr X " + commons.getNumberofDnaseinChrX()+ "\n");
		System.out.print("Number of dnase in Chr Y " + commons.getNumberofDnaseinChrY()+ "\n");
	
		System.out.print("Total number of dnase  intervals" + commons.getTotalNumberofDnase()+ "\n");
		
	}
	
//	Write Histone Information to the console
	public void writeHistoneInformationtoConsole(){
		
		System.out.print("Number of histone in Chr 1 " + commons.getNumberofHistoneinChr1()+ "\n");
		System.out.print("Number of histone in Chr 2 " + commons.getNumberofHistoneinChr2()+ "\n");
		System.out.print("Number of histone in Chr 3 " + commons.getNumberofHistoneinChr3()+ "\n");
		System.out.print("Number of histone in Chr 4 " + commons.getNumberofHistoneinChr4()+ "\n");
		System.out.print("Number of histone in Chr 5 " + commons.getNumberofHistoneinChr5()+ "\n");
		System.out.print("Number of histone in Chr 6 " + commons.getNumberofHistoneinChr6()+ "\n");
		System.out.print("Number of histone in Chr 7 " + commons.getNumberofHistoneinChr7()+ "\n");
		System.out.print("Number of histone in Chr 8 " + commons.getNumberofHistoneinChr8()+ "\n");
		System.out.print("Number of histone in Chr 9 " + commons.getNumberofHistoneinChr9()+ "\n");
		System.out.print("Number of histone in Chr 10 " + commons.getNumberofHistoneinChr10()+ "\n");
		System.out.print("Number of histone in Chr 11 " + commons.getNumberofHistoneinChr11()+ "\n");
		System.out.print("Number of histone in Chr 12 " + commons.getNumberofHistoneinChr12()+ "\n");
		System.out.print("Number of histone in Chr 13 " + commons.getNumberofHistoneinChr13()+ "\n");
		System.out.print("Number of histone in Chr 14 " + commons.getNumberofHistoneinChr14()+ "\n");
		System.out.print("Number of histone in Chr 15 " + commons.getNumberofHistoneinChr15()+ "\n");
		System.out.print("Number of histone in Chr 16 " + commons.getNumberofHistoneinChr16()+ "\n");
		System.out.print("Number of histone in Chr 17 " + commons.getNumberofHistoneinChr17()+ "\n");
		System.out.print("Number of histone in Chr 18 " + commons.getNumberofHistoneinChr18()+ "\n");
		System.out.print("Number of histone in Chr 19 " + commons.getNumberofHistoneinChr19()+ "\n");
		System.out.print("Number of histone in Chr 20 " + commons.getNumberofHistoneinChr20()+ "\n");
		System.out.print("Number of histone in Chr 21 " + commons.getNumberofHistoneinChr21()+ "\n");
		System.out.print("Number of histone in Chr 22 " + commons.getNumberofHistoneinChr22()+ "\n");
		System.out.print("Number of histone in Chr X " + commons.getNumberofHistoneinChrX()+ "\n");
		System.out.print("Number of histone in Chr Y " + commons.getNumberofHistoneinChrY()+ "\n");
	
		System.out.print("Total number of histone intervals " + commons.getTotalNumberofHistone()+ "\n");
			
	}
	
	public void readUnsortedChromBaseHistoneFilesSortWriteSortedChromosomeBaseHistoneFiles(){
		
		int indexofFirstTab=0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		
		//Read the unsorted chromosome base histone file into a list
		for(int i=1; i<=24; i++){
			FileReader fileReader = null;
			BufferedReader br = null;	
			String strLine;
			
			FileWriter fileWriter = null;
			BufferedWriter bw = null;
			
			try {								
					switch (i) {
		            case 1:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_HISTONE);
			        		fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR1_HISTONE);
		                    break;
		            case 2:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR2_HISTONE);
	        				break;
		            case 3:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR3_HISTONE);
	        				break;
		            case 4:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR4_HISTONE);
	        				break;
		            case 5:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR5_HISTONE);
	        				break;
		            case 6:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR6_HISTONE);
	        				break;
		            case 7:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR7_HISTONE);
	        				break;
		            case 8:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR8_HISTONE);
	        				break;
		            case 9:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR9_HISTONE);
	        				break;
		            case 10:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR10_HISTONE);
	        				break;
		            case 11:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR11_HISTONE);
	        				break;
		            case 12:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR12_HISTONE);
	        				break;
		            case 13:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR13_HISTONE);
	        				break;
		            case 14:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR14_HISTONE);
	        				break;
		            case 15:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR15_HISTONE);
	        				break;
		            case 16:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR16_HISTONE);
	        				break;
		            case 17:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR17_HISTONE);
	        				break;
		            case 18:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR18_HISTONE);
	        				break;
		            case 19:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR19_HISTONE);
	        				break;
		            case 20:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR20_HISTONE);
	        				break;
		            case 21:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR21_HISTONE);
	        				break;
		            case 22:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR22_HISTONE);
	        				break;
		            case 23:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRX_HISTONE);
	        				break;
		            case 24:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_HISTONE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRY_HISTONE);
                    break;
		        }
									
					br = new BufferedReader(fileReader);					
					bw = new BufferedWriter(fileWriter);

					histoneIntervalTree = new IntervalTree();
					Histone histone = new Histone();
					
					try {
						while ((strLine = br.readLine()) != null)   {
							  // ADD the content to the ArrayList
							
							indexofFirstTab = strLine.indexOf('\t');
							indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
							indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
							indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
							
							if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0) || indexofFifthTab <0){
								System.out.println("Unexpected histone format in Unsorted Histone File");
								System.out.println("For chromosome " + i);
								System.out.println(strLine);								
							}
							
							histone.setChromName(strLine.substring(0, indexofFirstTab));							
							histone.setStartPos(Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab)));
							histone.setEndPos(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));							
							histone.setHistoneName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
							histone.setCellLineName(strLine.substring(indexofFourthTab+1, indexofFifthTab));
							histone.setFileName(strLine.substring(indexofFifthTab+1));
							
							IntervalTreeNode node = new IntervalTreeNode(histone.getChromName(), histone.getStartPos(), histone.getEndPos(), histone.getHistoneName(), histone.getCellLineName(), histone.getFileName(),Commons.ORIGINAL_NODE);
							
							histoneIntervalTree.intervalTreeInsert(histoneIntervalTree,node);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
//					write sorted histone list to file
					histoneIntervalTree.intervalTreeInfixTraversal(histoneIntervalTree.getRoot(), bw, Commons.HISTONE);
					
//					Remove memory allocation for histoneList
					histoneIntervalTree = null;
					histone = null;
					
					try {
						br.close();
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
		} // open unsorted chrom base histone file one by one
	
	}
	
	public void readUnsortedChromBaseDnaseFilesSortWriteSortedChromosomeBaseDnaseFiles(){
		int indexofFirstTab=0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		
		//Read the unsorted chromosome base dnase file into a list
		for(int i=1; i<=24; i++){
			FileReader fileReader = null;
			BufferedReader br = null;	
			String strLine;
			
			FileWriter fileWriter = null;
			BufferedWriter bw = null;
			
			try {								
					switch (i) {
		            case 1:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_DNASE);
			        		fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR1_DNASE);
		                    break;
		            case 2:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR2_DNASE);
	        				break;
		            case 3:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR3_DNASE);
	        				break;
		            case 4:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR4_DNASE);
	        				break;
		            case 5:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR5_DNASE);
	        				break;
		            case 6:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR6_DNASE);
	        				break;
		            case 7:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR7_DNASE);
	        				break;
		            case 8:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR8_DNASE);
	        				break;
		            case 9:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR9_DNASE);
	        				break;
		            case 10:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR10_DNASE);
	        				break;
		            case 11:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR11_DNASE);
	        				break;
		            case 12:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR12_DNASE);
	        				break;
		            case 13:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR13_DNASE);
	        				break;
		            case 14:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR14_DNASE);
	        				break;
		            case 15:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR15_DNASE);
	        				break;
		            case 16:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR16_DNASE);
	        				break;
		            case 17:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR17_DNASE);
	        				break;
		            case 18:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR18_DNASE);
	        				break;
		            case 19:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR19_DNASE);
	        				break;
		            case 20:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR20_DNASE);
	        				break;
		            case 21:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR21_DNASE);
	        				break;
		            case 22:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR22_DNASE);
	        				break;
		            case 23:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRX_DNASE);
	        				break;
		            case 24:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_DNASE);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRY_DNASE);
                    break;
		        }
									
					br = new BufferedReader(fileReader);					
					bw = new BufferedWriter(fileWriter);

					dnaseIntervalTree = new IntervalTree();					
					Dnase dnase = new Dnase();
					
					try {
						while ((strLine = br.readLine()) != null)   {
							  // ADD the content to the ArrayList
							
							
							
							indexofFirstTab = strLine.indexOf('\t');
							indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
							indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
							
							if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0)){
								System.out.println("Unexpected tfbs format in Unsorted Dnase File");
								System.out.println("For chromosome " + i);
								System.out.println(strLine);								
							}
							
							dnase.setChromName(strLine.substring(0, indexofFirstTab));							
							dnase.setStartPos(Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab)));
							dnase.setEndPos(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));							
							dnase.setCellLineName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
							dnase.setFileName(strLine.substring(indexofFourthTab+1));

							IntervalTreeNode node = new IntervalTreeNode(dnase.getChromName(),dnase.getStartPos(), dnase.getEndPos(),dnase.getCellLineName(), dnase.getFileName(),Commons.ORIGINAL_NODE);							
							
							dnaseIntervalTree.intervalTreeInsert(dnaseIntervalTree,node);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
//					write sorted dnase Interval Tree to file
					dnaseIntervalTree.intervalTreeInfixTraversal(dnaseIntervalTree.getRoot(), bw, Commons.DNASE);
					
					
//					Remove memory allocation for dnaseList
					dnaseIntervalTree = null;
					dnase = null;
					
					try {
						br.close();
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
		} // open unsorted chrom base dnase file one by one
	
	}
	
	public void readUnsortedChromBaseTfbsFilesSortWriteSortedChromosomeBaseTfbsFiles(){
		
		int indexofFirstTab=0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		
		//Read the unsorted chromosome base tfbs file into a list
		for(int i=1; i<=24; i++){
			FileReader fileReader = null;
			BufferedReader br = null;	
			String strLine;
			
			FileWriter fileWriter = null;
			BufferedWriter bw = null;
			
			try {								
					switch (i) {
		            case 1:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_TFBS);
			        		fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR1_TFBS);
		                    break;
		            case 2:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR2_TFBS);
	        				break;
		            case 3:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR3_TFBS);
	        				break;
		            case 4:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR4_TFBS);
	        				break;
		            case 5:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR5_TFBS);
	        				break;
		            case 6:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR6_TFBS);
	        				break;
		            case 7:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR7_TFBS);
	        				break;
		            case 8:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR8_TFBS);
	        				break;
		            case 9:	fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR9_TFBS);
	        				break;
		            case 10:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR10_TFBS);
	        				break;
		            case 11:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR11_TFBS);
	        				break;
		            case 12:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR12_TFBS);
	        				break;
		            case 13:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR13_TFBS);
	        				break;
		            case 14:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR14_TFBS);
	        				break;
		            case 15:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR15_TFBS);
	        				break;
		            case 16:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR16_TFBS);
	        				break;
		            case 17:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR17_TFBS);
	        				break;
		            case 18:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR18_TFBS);
	        				break;
		            case 19:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR19_TFBS);
	        				break;
		            case 20:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR20_TFBS);
	        				break;
		            case 21:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR21_TFBS);
	        				break;
		            case 22:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR22_TFBS);
	        				break;
		            case 23:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRX_TFBS);
	        				break;
		            case 24:fileReader = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_TFBS);
	        				fileWriter = new FileWriter(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRY_TFBS);
                    break;
		        }
									
					br = new BufferedReader(fileReader);					
					bw = new BufferedWriter(fileWriter);

					tfbsIntervalTree = new IntervalTree ();
					TranscriptionFactorBindingSite tfbs = new TranscriptionFactorBindingSite();
					
					try {
						while ((strLine = br.readLine()) != null)   {
							  // ADD the content to the ArrayList
							
							indexofFirstTab = strLine.indexOf('\t');
							indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
							indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
							indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
							
							if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0) || indexofFifthTab <0){
								System.out.println("Unexpected tfbs format in Unsorted Tfbs File");
								System.out.println("For chromosome " + i);
								System.out.println(strLine);								
							}
							
							tfbs.setChromName(strLine.substring(0, indexofFirstTab));							
							tfbs.setStartPos(Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab)));
							tfbs.setEndPos(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));							
							tfbs.setTranscriptionFactorName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
							tfbs.setCellLineName(strLine.substring(indexofFourthTab+1, indexofFifthTab));
							tfbs.setFileName(strLine.substring(indexofFifthTab+1));
							
							IntervalTreeNode node = new IntervalTreeNode(tfbs.getChromName(), tfbs.getStartPos(), tfbs.getEndPos(), tfbs.getTranscriptionFactorName(), tfbs.getCellLineName(), tfbs.getFileName(),Commons.ORIGINAL_NODE);
							tfbsIntervalTree.intervalTreeInsert(tfbsIntervalTree, node);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

//					write sorted tfbs list to file
					tfbsIntervalTree.intervalTreeInfixTraversal(tfbsIntervalTree.getRoot(), bw, Commons.TFBS);
					
//					Remove memory allocation for tfbsList
					tfbsIntervalTree = null;
					tfbs = null;
					
					try {
						br.close();
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
		} // open unsorted chrom base tfbs file one by one
		
		
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File dnaseDir1 	= new File(common.Commons.FTP_ENCODE_DNASE_DIRECTORY1);
		File dnaseDir2 	= new File(common.Commons.FTP_ENCODE_DNASE_DIRECTORY2);		
		File tfbsDir 	= new File(common.Commons.FTP_ENCODE_TFBS_DIRECTORY);		
		File histoneDir = new File(common.Commons.FTP_ENCODE_HISTONE_DIRECTORY);
		
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting annotateUsingEncode = new CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting();

		//Attention
		//Encode data
		// The starting position of the feature in the chromosome. The first base in a chromosome is numbered 0.
		// The ending position of the feature in the chromosome or scaffold. 
		// The chromEnd base is not included in the display of the feature. 
		//For example, the first 100 bases of a chromosome are defined as chromStart=0, chromEnd=100, and span the bases numbered 0-99.
		
		//my convention design
		// start 0-based
		// start and end are inclusive
		// therefore length is equal to end-start+1.

//		DNASE
		annotateUsingEncode.openUnsortedChromosomeBaseDnaseFiles();
		annotateUsingEncode.readEncodeDnaseFilesandWriteUnsortedChromBaseDnaseFiles(dnaseDir2);		
		annotateUsingEncode.readEncodeDnaseFilesandWriteUnsortedChromBaseDnaseFiles(dnaseDir1);
		annotateUsingEncode.closeUnsortedChromosomeBaseFiles();		
		annotateUsingEncode.readUnsortedChromBaseDnaseFilesSortWriteSortedChromosomeBaseDnaseFiles();
		annotateUsingEncode.writeDnaseInformationtoConsole();

//		HISTONE
		annotateUsingEncode.openUnsortedChromosomeBaseHistoneFiles();
		annotateUsingEncode.readEncodeHistoneFilesandWriteUnsortedChromBaseHistoneFiles(histoneDir);		
		annotateUsingEncode.closeUnsortedChromosomeBaseFiles();
		annotateUsingEncode.readUnsortedChromBaseHistoneFilesSortWriteSortedChromosomeBaseHistoneFiles();
		annotateUsingEncode.writeHistoneInformationtoConsole();
				
//		TFBS
		annotateUsingEncode.openUnsortedChromosomeBaseTfbsFiles();
		annotateUsingEncode.readEncodeTfbsFilesandWriteUnsortedChromBaseTfbsFiles(tfbsDir);		
		annotateUsingEncode.closeUnsortedChromosomeBaseFiles();		
		annotateUsingEncode.readUnsortedChromBaseTfbsFilesSortWriteSortedChromosomeBaseTfbsFiles();
		annotateUsingEncode.writeTfbsInformationtoConsole();
				            
	}

}