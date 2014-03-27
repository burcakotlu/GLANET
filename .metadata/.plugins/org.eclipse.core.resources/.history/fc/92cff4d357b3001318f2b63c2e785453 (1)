/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

package testlinearsearchversusintervaltreesearch;


public class CompareLinearSearchVersusIntervalTreeSearch {
	
	public void writeCommonObject(CommonCompareObject object, BufferedWriter bufferedWriter){
		try {
			if(object.getObjectName().equals("ucscRefSeqGene")){				
					bufferedWriter.write(object.getObjectName() + "\t" + object.getChromName()  +  "\t" + object.getLow() + "\t" + object.getHigh() + "\t" +object.getRefSeqName() + "\t"  + object.getIntervalName() + "\t" + object.getHugoSymbol() + "\t" + object.getEntrezId()+ "\n" );				
					bufferedWriter.flush();
			}else if((object.getObjectName().equals("tfbs")) || (object.getObjectName().equals("histone"))) {
				bufferedWriter.write(object.getObjectName() + "\t" + object.getChromName()  +  "\t" + object.getLow() + "\t" + object.getHigh() + "\t" +object.getTfbsorHistoneName() + "\t"  + object.getCellLineName() + "\t" + object.getFileName()+ "\n" );				
				bufferedWriter.flush();
				
			}else if(object.getObjectName().equals("dnase")){
				bufferedWriter.write(object.getObjectName() + "\t" + object.getChromName()  +  "\t" + object.getLow() + "\t" + object.getHigh() + "\t"  + object.getCellLineName() + "\t" + object.getFileName()+ "\n" );				
				bufferedWriter.flush();
				
			}else{
				
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean contains(CommonCompareObject object, Set<CommonCompareObject> set){
		Iterator<CommonCompareObject> itr = set.iterator();
		
		while(itr.hasNext()){
			CommonCompareObject  setObject = (CommonCompareObject)itr.next();
			if (object.equals(setObject))
				return true; 												
		}
		return false;
	}
	
	public void compare(Set<CommonCompareObject> intervalTreeResultSet, Set<CommonCompareObject> linearSearchResultSet, BufferedWriter bufferedWriter){
		 try {
			 if	((linearSearchResultSet.size() != intervalTreeResultSet.size())){
				 	
				 	bufferedWriter.write("Interval Tree Result Set Size" + "\t" + intervalTreeResultSet.size()+  "\n");
					bufferedWriter.write("Linear Search Result Set Size" + "\t" + linearSearchResultSet.size()+  "\n");
					
					bufferedWriter.write("Set sizes are not equal" + "\n");		
					bufferedWriter.flush();					
			 }			 
			 else{
				 
				 
				
				bufferedWriter.write("Interval Tree Result Set Size" + "\t" + intervalTreeResultSet.size()+  "\n");
				bufferedWriter.write("Linear Search Result Set Size" + "\t" + linearSearchResultSet.size()+  "\n");
				
				Iterator<CommonCompareObject> itr1 = intervalTreeResultSet.iterator();
				Iterator<CommonCompareObject> itr2 = linearSearchResultSet.iterator();
				
				boolean problem = false; 
				
				while(itr1.hasNext()){
					CommonCompareObject commonObject = (CommonCompareObject) itr1.next();
					if (!contains(commonObject, linearSearchResultSet)){
							problem = true;
					}
				}
				
				while(itr2.hasNext()){
					CommonCompareObject commonObject = (CommonCompareObject) itr2.next();
					if (!contains(commonObject, intervalTreeResultSet)){
							problem = true;
					}
				}
				
				if (!problem){
					bufferedWriter.write("Sets are equal" + "\n");		
					bufferedWriter.flush();				
				} else{
					bufferedWriter.write("Sets are not equal" + "\n");		
					bufferedWriter.flush();								
				}
				
			 }
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			 
		
			
		
	}
	
	
//	public void compare(List<String> intervalTreeResultList,List<String> linearSearchResultList, BufferedWriter bufferedWriter){
//		try {
//			
//				
//				if(intervalTreeResultList.size()!= linearSearchResultList.size()){
//					bufferedWriter.write("Their size are not equal" + "\n");
//				}else if(intervalTreeResultList.containsAll(linearSearchResultList) && linearSearchResultList.containsAll(intervalTreeResultList)){
//					bufferedWriter.write("Their size are equal" + "\n");
//					bufferedWriter.write("These sets are equal" + "\n");
//				}else{
//					bufferedWriter.write("Their size are equal" + "\n");
//					bufferedWriter.write("These sets are not equal" + "\n");
//				
//					bufferedWriter.write("Interval Tree Result List" + "\n");
//					for(int i=0; i<intervalTreeResultList.size(); i++){
//						bufferedWriter.write(intervalTreeResultList.get(i) + "\n");
//						bufferedWriter.flush();
//					}
//					
//					bufferedWriter.write("Linear Search Result List" + "\n");
//					for(int i=0; i<linearSearchResultList.size(); i++){
//						bufferedWriter.write(linearSearchResultList.get(i) + "\n");
//						bufferedWriter.flush();
//					}
//				}
//			
//		
//			
//		} catch (IOException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public CommonCompareObject createSetObject(String strLine){
		
		CommonCompareObject object = null;
		
		String objectName;
		String chromName;
		int low;
		int high;
		String cellLineName;
		String fileName;
		String tfbsorHistoneName;
		String refSeqName;
		String intervalName;
		String hugoSymbol;
		int entrezId;
		
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
		int indexofFifthTab =0;
		int indexofSixthTab =0;
		int indexofSeventhTab =0;
		
		indexofFirstTab = strLine.indexOf('\t');
		indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
		indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
		indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
		indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
		
		indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
		
		if (indexofSixthTab >0){
			indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
		}
		
		
		if (indexofSeventhTab>0){
//			ucscRrefSeqGene Case
//			ucscRefSeqGene	chrY	4966255	4968748	NM_032971	Exon5	PCDH11Y	83259

			objectName = strLine.substring(0, indexofFirstTab);
			chromName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
			low = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
			high = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
			refSeqName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
			intervalName = strLine.substring(indexofFifthTab+1, indexofSixthTab);
			hugoSymbol = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
			entrezId = Integer.parseInt(strLine.substring(indexofSeventhTab+1));
			
			object = new CommonCompareObject(objectName,chromName,low,high,refSeqName,intervalName,hugoSymbol,entrezId);
			
			
			
		}else if (indexofSixthTab>0){
//			tfbs case or histone case
//			tfbs	chrX	47775509	47775893	Znf274	Nt2d1	spp.optimal.wgEncodeSydhTfbsNt2d1Znf274UcdAlnRep0_VS_wgEncodeSydhTfbsNt2d1InputUcdAlnRep1.narrowPeak
//			histone	chrX	129370240	129371154	H3k36me3b	Nt2d1	wgEncodeSydhHistoneNt2d1H3k36me3bUcdAln.narrowPeak

			objectName = strLine.substring(0, indexofFirstTab);
			chromName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
			low = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
			high = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
			tfbsorHistoneName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
			cellLineName = strLine.substring(indexofFifthTab+1, indexofSixthTab);
			fileName = strLine.substring(indexofSixthTab+1);
			
			object = new CommonCompareObject(objectName,chromName,low,high,tfbsorHistoneName,cellLineName,fileName);
			
		}else{
//			dnase case
			objectName = strLine.substring(0, indexofFirstTab);
			chromName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
			low = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
			high = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
			cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
			fileName = strLine.substring(indexofFifthTab+1);

			object = new CommonCompareObject(objectName,chromName,low,high,cellLineName,fileName);
		}
		
		
		return object;
	}
	
	
	public void compareSearchOutputFilesUsingSet(BufferedReader bufferedReader1, BufferedReader bufferedReader2){
		String strLine1;
		String strLine2;
		
		Set<CommonCompareObject> intervalTreeResultSet = new HashSet<CommonCompareObject>();
		Set<CommonCompareObject> linearSearchResultSet = new HashSet<CommonCompareObject>();
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(Commons.TEST_LINEAR_SEARCH_VERSUS_INTERVAL_TREE_SEARCH);
			bufferedWriter = new BufferedWriter(fileWriter);

		
			strLine1 = bufferedReader1.readLine();
			strLine2 = bufferedReader2.readLine();
			
			while((strLine1!=null)  && (strLine2 !=null)){

//				this is a Searched for line
//			 	read till the next Searched for line
//				and fill the result list
				intervalTreeResultSet.clear();
				linearSearchResultSet.clear();
				
				
				if (strLine1.startsWith("Searched for")){
					bufferedWriter.write(strLine1 + "\n");
					
					while (((strLine1= bufferedReader1.readLine())!=null) && !(strLine1.startsWith("Searched for"))){						
						intervalTreeResultSet.add(createSetObject(strLine1));
						
					}
				}
				
				if (strLine2.startsWith("Searched for")){
					bufferedWriter.write(strLine2 + "\n");
					
						while (((strLine2= bufferedReader2.readLine())!=null) && !(strLine2.startsWith("Searched for"))){
							linearSearchResultSet.add(createSetObject(strLine2));
						}
				}
				
				
//				compare this result lists				
				compare(intervalTreeResultSet,linearSearchResultSet,bufferedWriter);
			
				bufferedWriter.write("------------------------------------------\n");
			}// End of While
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	public void compareSearchOutputFilesUsingList(BufferedReader bufferedReader1, BufferedReader bufferedReader2){
//		String strLine1;
//		String strLine2;
//		
//		List<String> intervalTreeResultList = new ArrayList<String>();
//		List<String> linearSearchResultList = new ArrayList<String>();
//		
//		FileWriter fileWriter = null;
//		BufferedWriter bufferedWriter = null;
//		try {
//			fileWriter = new FileWriter(Commons.TEST_LINEAR_SEARCH_VERSUS_INTERVAL_TREE_SEARCH);
//			bufferedWriter = new BufferedWriter(fileWriter);
//
//		
//			strLine1 = bufferedReader1.readLine();
//			strLine2 = bufferedReader2.readLine();
//			
//			while((strLine1!=null)  && (strLine2 !=null)){
//
////				this is a Searched for line
////			 	read till the next Searched for line
////				and fill the result list
//				intervalTreeResultList.clear();
//				linearSearchResultList.clear();
//				
//				
//				if (strLine1.startsWith("Searched for")){
//					bufferedWriter.write(strLine1 + "\n");
//					
//					while (((strLine1= bufferedReader1.readLine())!=null) && !(strLine1.startsWith("Searched for"))){
//						intervalTreeResultList.add(strLine1);
//					}
//				}
//				
//				if (strLine2.startsWith("Searched for")){
//					bufferedWriter.write(strLine2 + "\n");
//					
//						while (((strLine2= bufferedReader2.readLine())!=null) && !(strLine2.startsWith("Searched for"))){
//							linearSearchResultList.add(strLine2);
//						}
//				}
//				
//				
////				compare this result lists
//			
//				compare(intervalTreeResultList,linearSearchResultList,bufferedWriter);
//			
//				bufferedWriter.write("------------------------------------------\n");
//			}// End of While
//			
//			bufferedWriter.close();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	public void readFilesCompare(){
		FileReader fileReader1 = null;
		FileReader fileReader2 = null;
		BufferedReader bufferedReader1 = null;
		BufferedReader bufferedReader2 = null;
		
		try {
			fileReader1 = new FileReader(Commons.SEARCH_USING_INTERVAL_TREE_OUTPUT_FILE);
			fileReader2 = new FileReader(Commons.SEARCH_USING_LINEAR_SEARCH_OUTPUT_FILE);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bufferedReader1 = new BufferedReader(fileReader1);
		bufferedReader2 = new BufferedReader(fileReader2);
		
//		compareSearchOutputFilesUsingList(bufferedReader1,bufferedReader2);
		compareSearchOutputFilesUsingSet(bufferedReader1,bufferedReader2);
		
		try {
			bufferedReader1.close();
			bufferedReader2.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompareLinearSearchVersusIntervalTreeSearch compare = new CompareLinearSearchVersusIntervalTreeSearch();
		
		compare.readFilesCompare();

	}

}
