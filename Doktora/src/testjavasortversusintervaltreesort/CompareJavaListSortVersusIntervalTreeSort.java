/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package testjavasortversusintervaltreesort;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import common.Commons;


public class CompareJavaListSortVersusIntervalTreeSort {
	
	
	public void compareSortedFiles(BufferedReader bufferedReader1, BufferedReader bufferedReader2){
		String strLine1;
		String strLine2;
		
		int indexofFirstTab1;
		int indexofSecondTab1;
		int indexofThirdTab1;
		
		int indexofFirstTab2;
		int indexofSecondTab2;
		
		int startPos1;
		int endPos1;
		
		int startPos2;
		int endPos2;
		
		try {
			while(((strLine1 = bufferedReader1.readLine())!=null) &&  ((strLine2 = bufferedReader2.readLine())!=null)){
				
				indexofFirstTab1 = strLine1.indexOf('\t');
				indexofSecondTab1 = strLine1.indexOf('\t', indexofFirstTab1+1);
				indexofThirdTab1 = strLine1.indexOf('\t',indexofSecondTab1+1);

				indexofFirstTab2 = strLine2.indexOf('\t');
				indexofSecondTab2 = strLine2.indexOf('\t', indexofFirstTab2+1);
				
				startPos1 = Integer.parseInt(strLine1.substring(indexofFirstTab1+1, indexofSecondTab1));
				endPos1 = Integer.parseInt(strLine1.substring(indexofSecondTab1+1, indexofThirdTab1));
				
				
				startPos2 = Integer.parseInt(strLine2.substring(0, indexofFirstTab2));
				endPos2 = Integer.parseInt(strLine2.substring(indexofFirstTab2+1, indexofSecondTab2));
				
				if((startPos1!=startPos2) || (endPos1 != endPos2))
					System.out.println("Not equal");
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readBothFilesCompare(){
		
		FileReader fileReader1 = null;
		FileReader fileReader2 = null;
		BufferedReader bufferedReader1 = null;
		BufferedReader bufferedReader2 = null;
		
		try {
			for(int i= 1; i<=24; i++){
				switch(i){
				case 1: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR1_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR1_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 2: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR2_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR2_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 3: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR3_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR3_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 4: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR4_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR4_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 5: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR5_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR5_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 6: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR6_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR6_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 7: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR7_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR7_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 8: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR8_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR8_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 9: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR9_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR9_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 10: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR10_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR10_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 11: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR11_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR11_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 12: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR12_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR12_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 13: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR13_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR13_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 14: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR14_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR14_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 15: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR15_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR15_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 16: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR16_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR16_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 17: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR17_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR17_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 18: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR18_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR18_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 19: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR19_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR19_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 20: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR20_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR20_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 21: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR21_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR21_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 22: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHR22_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR22_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 23: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRX_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRX_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				case 24: fileReader1 = new FileReader(Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.SORTED_CHRY_REFSEQ_GENES);
						fileReader2 = new FileReader(Commons.C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRY_UCSC_REFSEQ_GENES_INTERVAL_TREE);
						break;
				
				}
				
				bufferedReader1 = new BufferedReader(fileReader1);
				bufferedReader2 = new BufferedReader(fileReader2);
			}// End of for
		
			compareSortedFiles(bufferedReader1,bufferedReader2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void main(String[] args) {
		CompareJavaListSortVersusIntervalTreeSort compare = new CompareJavaListSortVersusIntervalTreeSort();
		compare.readBothFilesCompare();
	}

}
