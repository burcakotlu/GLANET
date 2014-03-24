/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import common.Commons;






public class HashMapVersusListTest {
	
	public void fillList(List<String> list, String inputFileName){
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(inputFileName);			
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null) {			
				list.add(strLine);
				strLine = null;
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}


	public BufferedWriter setStringandBufferedWriter(char[] name, List<StringandBufferedWriter> list){

		for(int i = 0; i< list.size(); i++){
			if (Arrays.equals(name,list.get(i).getName().toCharArray())){
				return list.get(i).getBufferedWriter();
			}
		}		
		
		return null;
	}
	
	public BufferedWriter setCharArrayandBufferedWriter(char[] name, List<CharArrayandBufferedWriter> list){

		for(int i = 0; i< list.size(); i++){
			if (Arrays.equals(name,list.get(i).getName())){
				return list.get(i).getBufferedWriter();
			}
		}		
		
		return null;
	}
	
	
	public BufferedWriter setStringBufferedWriterMap(String name, Map<String, BufferedWriter> mapwithString){
		BufferedWriter bufferedWriter = null;
		
		if (mapwithString.containsKey(name)){
			bufferedWriter = mapwithString.get(name);
		}
		return bufferedWriter;
	}
	
	
	public BufferedWriter setCharArrayBufferedWriterMap(char[] name, Map<char[],BufferedWriter> mapwithCharArray){
		BufferedWriter bufferedWriter = null;
		
		if (mapwithCharArray.containsKey(name)){
			bufferedWriter = mapwithCharArray.get(name);
		}
		return bufferedWriter;
	}
	
	public void functionListString(List<StringandBufferedWriter>  listwithString, List<char[]> ncbiRnaList){
		for(int i = 1; i<ncbiRnaList.size(); i ++){
			BufferedWriter bufferedWriter = setStringandBufferedWriter(ncbiRnaList.get(i), listwithString);
			try {
				if (bufferedWriter == null){
					FileWriter fileWriter;
					
					fileWriter = new FileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_RNA +"_" + String.valueOf(ncbiRnaList.get(i)) + ".txt");
					
					bufferedWriter = new BufferedWriter(fileWriter);
					listwithString.add(new StringandBufferedWriter(String.valueOf(ncbiRnaList.get(i)), bufferedWriter));							
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}// End of for
	}
	
	
	public void functionListCharArray(List<CharArrayandBufferedWriter> listwithCharArray, List<char[]> ncbiRnaList){
		for(int i = 1; i<ncbiRnaList.size(); i++){
			BufferedWriter bufferedWriter = setCharArrayandBufferedWriter(ncbiRnaList.get(i), listwithCharArray);
			try {
				if (bufferedWriter == null){
					FileWriter fileWriter;
					
					fileWriter = new FileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_RNA +"_" + String.valueOf(ncbiRnaList.get(i)) + ".txt");
					
					bufferedWriter = new BufferedWriter(fileWriter);
					listwithCharArray.add(new CharArrayandBufferedWriter(ncbiRnaList.get(i), bufferedWriter));							
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}// End of for
	}
	
	
	public void functionMapString(Map<String,BufferedWriter> mapwithString,List<String> ncbiRnaList){
		for(int i = 1; i< ncbiRnaList.size(); i++){
			BufferedWriter bufferedWriter = setStringBufferedWriterMap(ncbiRnaList.get(i), mapwithString);
			try {
				if (bufferedWriter == null){
					FileWriter fileWriter;
					
					fileWriter = new FileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_RNA +"_" + String.valueOf(ncbiRnaList.get(i)) + ".txt");
					
					bufferedWriter = new BufferedWriter(fileWriter);
					mapwithString.put(ncbiRnaList.get(i), bufferedWriter);							
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			

		}// end of for 
		
	}
	
	
	public void functionMapCharArray(Map<char[],BufferedWriter> mapwithCharArray,List<char[]> ncbiRnaList){
		for(int i = 1; i< ncbiRnaList.size(); i++){
			BufferedWriter bufferedWriter = setCharArrayBufferedWriterMap(ncbiRnaList.get(i), mapwithCharArray);
			try {
				if (bufferedWriter == null){
					FileWriter fileWriter;
					
					fileWriter = new FileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_RNA +"_" + String.valueOf(ncbiRnaList.get(i)) + ".txt");
					
					bufferedWriter = new BufferedWriter(fileWriter);
					mapwithCharArray.put(ncbiRnaList.get(i), bufferedWriter);							
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			

		}// end of for 
		
	}
	
	public void closeStringandBufferedWriterList(List<StringandBufferedWriter>  listwithString){
		for(int i= 0; i<listwithString.size(); i++){
			try {
				listwithString.get(i).getBufferedWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeCharArrayandBufferedWriterList(List<CharArrayandBufferedWriter>  listwithCharArray){
		for(int i= 0; i<listwithCharArray.size(); i++){
			try {
				listwithCharArray.get(i).getBufferedWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void closeStringBufferedWriterMap(Map<String, BufferedWriter>  mapStringBufferedWriter){
		Iterator<BufferedWriter> itr =mapStringBufferedWriter.values().iterator();
		
		while(itr.hasNext()){
			BufferedWriter bufferedWriter = (BufferedWriter) itr.next();
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	

	public void closeCharArrayBufferedWriterMap(Map<char[], BufferedWriter>  mapCharArrayBufferedWriter){
		Iterator<BufferedWriter> itr =mapCharArrayBufferedWriter.values().iterator();
		
		while(itr.hasNext()){
			BufferedWriter bufferedWriter = (BufferedWriter) itr.next();
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		HashMapVersusListTest hashMapVersusListTest = new HashMapVersusListTest();
		
//		List<char[]> ncbiRnaNucleotideAccessionVersionList = new ArrayList<char[]>();
		List<String> ncbiRnaNucleotideAccessionVersionList = new ArrayList<String>();

		hashMapVersusListTest.fillList(ncbiRnaNucleotideAccessionVersionList,Commons.WRITE_ALL_POSSIBLE_RNA_NUCLEUOTIDE_ACCESSION_VERSIONS_OUTPUT_DIRECTORYNAME + Commons.WRITE_ALL_POSSIBLE_RNA_NUCLEUOTIDE_ACCESSION_VERSIONS_OUTPUT_FILENAME);

//		List<StringandBufferedWriter> listwithString = new ArrayList<StringandBufferedWriter>();
//		hashMapVersusListTest.functionListString(listwithString,ncbiRnaNucleotideAccessionVersionList);		
//		hashMapVersusListTest.closeStringandBufferedWriterList(listwithString);
		

//		List<CharArrayandBufferedWriter> listwithCharArray = new ArrayList<CharArrayandBufferedWriter>();
//		hashMapVersusListTest.functionListCharArray(listwithCharArray,ncbiRnaNucleotideAccessionVersionList);
//		hashMapVersusListTest.closeCharArrayandBufferedWriterList(listwithCharArray);
		
		Map<String,BufferedWriter> mapwithString = new HashMap<String, BufferedWriter>();
		hashMapVersusListTest.functionMapString(mapwithString,ncbiRnaNucleotideAccessionVersionList);
		hashMapVersusListTest.closeStringBufferedWriterMap(mapwithString);
		
		
//		Map<char[],BufferedWriter> mapwithCharArray = new HashMap<char[], BufferedWriter>();
//		hashMapVersusListTest.functionMapCharArray(mapwithCharArray,ncbiRnaNucleotideAccessionVersionList);
//		hashMapVersusListTest.closeCharArrayBufferedWriterMap(mapwithCharArray);
		
		}
	
	
	
}
