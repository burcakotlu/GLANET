/**
 * 
 */
package trees;

import intervaltree.Interval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import printing.Print;
import sorting.CountingSorting;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import enumtypes.SortingOrder;
import enumtypes.WriteAnnotationFoundOverlapsMode;
import gnu.trove.map.TIntByteMap;
import gnu.trove.map.TIntObjectMap;

/**
 * @author Burçak Otlu
 * @date Jun 28, 2016
 * @project Glanet 
 * 
 * Implemented from Computational Geometry, Mark de Berg
 * 
 * Assume that there are n intervals with 2n end points (each interval has two end points namely left and right end points)
 * Let x_mid be the median of 2n end points.
 * So at most half of the interval end points lies to the left of x_mid and at most half of the interval end points lies to the right of x_mid
 * Interval tree is a binary tree constructed based on this idea.
 * The right subtree of the tree stores I_right of the intervals that lie completely to the right of x_mid.   
 * The left subtree of the tree stores I_left of the intervals that lie completely to the left of x_mid.
 * The subtrees are constructed recursively in the same manner.
 * 
 * Root of the tree has its x_mid value and I_mid of of intervals containing (overlapping) this x_mid point.
 * Please notice that median of end points is not the average of end points.
 * 
 * Interval Tree Data Structure
 * 
 * 1. If I is empty set, then the interval tree is a leaf.
 * 2. Otherwise, let x_mid be the median of 2n end points.
 * 		Let 
 * 			I_left= {(x_left,x_right): x_right < x_mid}
 * 			I_mid=  {(x_left,x_right): x_left <= x_mid <= x_right}
 * 			I_right={(x_left,x_right): x_mid < x_left}
 * 
 * Interval tree consists of a root node v storing x_mid.
 * I_mid is stored in 2 lists, L_left is a list sorted in ascending left end points,
 * L_right is a list in descending right end points.
 * 
 * The left subtree of v is an interval tree for the set I_left. 
 * The right subtree of v is an interval tree for the set I_right. 
 * 
 * An interval tree of n intervals uses O(n) storage and has depth of O(logn).
 * 
 * Finding the median of a set of points take linear time.
 * 
 * It is better to find median by pre-sorting the set of points.
 * It is easy to maintain these pre-sorted sets through the recursive calls.
 * 
 * Let n_mid = card(I_mid)
 * Creating the lists, L_left and L_right takes O(n_mid log n_mid) time.
 * Hence time we spent is  O(n + n_mid log n_mid)
 * 
 * An interval tree of n intervals can be built in O(nlogn) time.
 *
 */
public class IntervalTreeMarkdeBerg {
	
	IntervalTreeMarkdeBergNode root;
	
	public IntervalTreeMarkdeBergNode getRoot() {
		return root;
	}

	public void setRoot(IntervalTreeMarkdeBergNode root) {
		this.root = root;
	}

	public IntervalTreeMarkdeBerg() {
		super();
	}

	// Generate Dnase Interval Tree with Numbers starts
	public static trees.IntervalTreeMarkdeBerg generateEncodeDnaseIntervalTreeWithNumbers(
			BufferedReader bufferedReader,
			BufferedWriter bufferedWriter) {

		trees.IntervalTreeMarkdeBerg dnaseIntervalTree = new IntervalTreeMarkdeBerg();
		String strLine = null;

		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;

		int startPosition = 0;
		int endPosition = 0;

//		ChromosomeName chromName;
		short cellLineNumber;
		short fileNumber;
		
		List<IntervalMarkdeBerg> intervalList = new ArrayList<IntervalMarkdeBerg>();

		try{
			while( ( strLine = bufferedReader.readLine()) != null){

				// old example strLine
				// chr1 91852781 91853156 GM12878
				// idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak

				// new example line with numbers
				// chrY 2709520 2709669 1 1

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
				indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);
				indexofFourthTab = strLine.indexOf( '\t', indexofThirdTab + 1);

//				chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));

				startPosition = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				endPosition = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				cellLineNumber = Short.parseShort( strLine.substring( indexofThirdTab + 1, indexofFourthTab));
				fileNumber = Short.parseShort( strLine.substring( indexofFourthTab + 1));

				// important note
				// while constructing the dnaseIntervalTree
				// we don't check for overlaps
				// we insert any given interval without overlap check

				DnaseIntervalMarkdeBerg interval = new DnaseIntervalMarkdeBerg(startPosition,endPosition,cellLineNumber,fileNumber);
				intervalList.add(interval);

			} // End of WHILE
			
			//Sort intervalList w.r.t. the left end points in ascending order.
			//Then all left, middle and right intervals will be added with left end points in ascending order.
			//Then you don't have to sort middle intervals w.r.t. left end points in in ascending order.
			//But only sort middle intervals w.r.t. right end points in in descending order.
			
			IntervalMarkdeBerg[] intervalArrayUnsorted = (IntervalMarkdeBerg[]) intervalList.toArray(new IntervalMarkdeBerg[intervalList.size()]);
			IntervalMarkdeBerg[] intervalArraySorted = new IntervalMarkdeBerg[intervalList.size()];
			
			//Do it only once. Sort intervals in ascending order w.r.t. left end points.
			CountingSorting.sortLeftEndPointsAscending(intervalArrayUnsorted, SortingOrder.SORTING_IN_ASCENDING_ORDER, intervalArraySorted);			
			
			//Free space
			intervalList = null;
			intervalArrayUnsorted = null;
			
			//Construct interval tree
			IntervalTreeMarkdeBergNode root = constructIntervalTree(intervalArraySorted,bufferedWriter);
			
			dnaseIntervalTree.setRoot(root);
			
		}catch( IOException e){
			System.out.println(e.toString());
		}

		return dnaseIntervalTree;
	}
	

	public static IntervalTreeMarkdeBerg createDnaseIntervalTreeWithNumbers(
			String dataFolder, 
			String outputFolder,
			ChromosomeName chrName){
		
		IntervalTreeMarkdeBerg intervalTree = null;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try{

			fileReader = FileOperations.createFileReader( 
					dataFolder + Commons.BYGLANET_ENCODE_DNASE_DIRECTORY,
					chrName.convertEnumtoString() + Commons.UNSORTED_ENCODE_DNASE_FILE_WITH_NUMBERS);
			
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + "IntervalsDistribution.txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			intervalTree = generateEncodeDnaseIntervalTreeWithNumbers(bufferedReader,bufferedWriter);
			
			//Close bufferedReader and bufferedWriter
			bufferedReader.close();
			bufferedWriter.close();

		}catch( FileNotFoundException e){
			System.out.println(e.toString());
		}catch( IOException e){
			System.out.println(e.toString());
		}
		
		return intervalTree; 
		
	}


	
	
	//allIntervals are not sorted whether w.r.t. to left or right end points
	//Take left and right end points and sort the all end points and find the median
	//Having median at hand, find the left node intervals such that right end point is less than median
	//Having median at hand, find the middle node intervals such that left end point is less than or equal to median and right end point is greater than or equal to median.
	//Having median at hand, find the right node intervals such that left end point is greater than median
	public static float findMedianAndFillLeftMiddleRightNodeIntervals(
			IntervalMarkdeBerg[] allIntervalsSorted,
			List<IntervalMarkdeBerg> leftNodeIntervals,
			List<IntervalMarkdeBerg> middleNodeIntervals,
			List<IntervalMarkdeBerg> rightNodeIntervals,
			BufferedWriter bufferedWriter){
		
		int numberofIntervals = allIntervalsSorted.length;
		int numberofEndPoints = numberofIntervals*2;
		
		int[] allEndPoints = new int[numberofEndPoints];
		int[] allEndPointsSorted = new int[numberofEndPoints];
		
		int j=0;
		int saved_i = allIntervalsSorted.length;

		float median;
		
		//Fill allEndPoints
		for(int i=0; i<allIntervalsSorted.length; i++){
			allEndPoints[j++] = allIntervalsSorted[i].getLow();
			allEndPoints[j++] = allIntervalsSorted[i].getHigh();
		}
		
		//We have to sort all end points in order to find the median.
		allEndPointsSorted = CountingSorting.sort(allEndPoints, SortingOrder.SORTING_IN_ASCENDING_ORDER);
		
		//Free space
		allEndPoints = null;
		
		//Find median
		median = (allEndPointsSorted[numberofIntervals-1] + allEndPointsSorted[numberofIntervals])*1.0f/2;
		
		//Fill left, middle and right interval lists
		//We have sorted all the intervals w.r.t. left end points in ascending order O(nlogn) or O(n+k)
		//Loop until you hit an interval with its low is greater than median
		//Then exit loop and add all the remaining intervals into right intervals.
		for(int i= 0 ; i<numberofIntervals; i++){
			
			if (allIntervalsSorted[i].getHigh() < median){
				leftNodeIntervals.add(allIntervalsSorted[i]);
			}else if (allIntervalsSorted[i].getLow() > median){
				rightNodeIntervals.add(allIntervalsSorted[i]);
				//We have inserted interval indexed at i
				//We know that rest of the intervals will be added to the rightNodeIntervals
				saved_i = i+1;
				break;
			}else{
				middleNodeIntervals.add(allIntervalsSorted[i]);
			}
		}//End of FOR
		
		
		//Check you don't put the cut off value twice
		for(int i= saved_i ; i<numberofIntervals; i++){
			rightNodeIntervals.add(allIntervalsSorted[i]);
		}
		
//		try {
//			bufferedWriter.write("Median: " + median + " Number of all intervals  is: " + allIntervalsSorted.length + "=[Left: " + leftNodeIntervals.size() +  ", Middle: " + middleNodeIntervals.size() + ", Right: " + rightNodeIntervals.size() + "]" + System.getProperty("line.separator"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
		
		//Sorting an array into ascending order. 
		//This can be done either sequentially, using the sort method, or concurrently, using the parallelSort method introduced in Java SE 8
		//Parallel sorting of large arrays on multiprocessor systems is faster than sequential array sorting.
		return median;
	}
	
	/*
	 * Sort middle intervals in two ways
	 * With respect to the left end points in ascending order
	 * With respect to the right end points in descending order
	 * Using counting sort
	 */
	public static void sortMiddleNodeIntervalsInTwoWays(
			List<IntervalMarkdeBerg> middleNodeIntervals,
			IntervalMarkdeBerg[] middleIntervalsLeftEndPointsAscending,
			IntervalMarkdeBerg[] middleIntervalsRightEndPointsDescending){
		
		//Does middleNodeIntervals has to be a list? Can it be stored in an array?
		//Yes it can be stored in an array of size all intervals
		//Or list can be converted into array
		//first requires more space, latter requires more space and time
		
		//middleNodeIntervals can be an empty list
		//In that case there is no need to convert it into an array
		//Then there is no need to sort this array in two ways
		if (middleNodeIntervals!=null && !middleNodeIntervals.isEmpty()){
			
			//Since it is already sorted w.r.t. left end points in ascending order
			//check it more
//			CountingSorting.sortLeftEndPointsAscending(middleIntervalsUnsorted, SortingOrder.SORTING_IN_ASCENDING_ORDER,middleIntervalsLeftEndPointsAscending);
			middleNodeIntervals.toArray(middleIntervalsLeftEndPointsAscending);
			CountingSorting.sortRightEndPointsDescending(middleIntervalsLeftEndPointsAscending, SortingOrder.SORTING_IN_DESCENDING_ORDER,middleIntervalsRightEndPointsDescending);
			
//			System.out.println("*********Middle Intervals Left End Points Ascending*************");
//			Print.printArray(middleIntervalsLeftEndPointsAscending);
//			System.out.println("*********Middle Intervals Right End Points Descending***********");
//			Print.printArray(middleIntervalsRightEndPointsDescending);
//			System.out.println("****************************************************************");
			
		}
		
	}
	
	
	//list contains unsorted intervals.
	//First sort the interval end points
	//Find the median
	//Find the node intervals
	//Find the left subtree intervals
	//Find the right subtree intervals
	public static IntervalTreeMarkdeBergNode constructIntervalTree(
			IntervalMarkdeBerg[] allIntervalsSorted,
			BufferedWriter bufferedWriter){
		
		IntervalTreeMarkdeBergNode node = null;
		
		if (allIntervalsSorted==null || allIntervalsSorted.length==0){
			
			return node;
			
		}else{
			
			Float median = new Float(0);
			List<IntervalMarkdeBerg> leftNodeIntervals = new ArrayList<>();
			List<IntervalMarkdeBerg> middleNodeIntervals = new ArrayList<>();
			List<IntervalMarkdeBerg> rightNodeIntervals = new ArrayList<>();
			
			IntervalTreeMarkdeBergNode left = null;
			IntervalTreeMarkdeBergNode right =null;
			
			
			//allIntervals can be an array
			//the filled list can be converted to an array
			//However we do not know how many of the intervals will be in left, middle and right.
			//By creating array of size of number of all intervals can be a solution.
			median = findMedianAndFillLeftMiddleRightNodeIntervals(allIntervalsSorted,leftNodeIntervals,middleNodeIntervals,rightNodeIntervals,bufferedWriter);
			
			//Free space
			allIntervalsSorted  = null;
						
			//We don't know the number of middle intervals
			//middleIntervalsLeftEndPointsAscending can be an array
			//middleIntervalsRightEndPointsDescending can be an array
			IntervalMarkdeBerg[] middleIntervalsLeftEndPointsAscending = new IntervalMarkdeBerg[middleNodeIntervals.size()];
			IntervalMarkdeBerg[] middleIntervalsRightEndPointsDescending = new IntervalMarkdeBerg[middleNodeIntervals.size()];
			
			sortMiddleNodeIntervalsInTwoWays(middleNodeIntervals,middleIntervalsLeftEndPointsAscending,middleIntervalsRightEndPointsDescending);
			
			//Free Space
			//We have created middleIntervalsLeftEndPointsAscending and middleIntervalsRightEndPointsDescending from middleNodeIntervals
			//So remove middleNodeIntervals
			middleNodeIntervals = null;
			
			left = constructIntervalTree(leftNodeIntervals.toArray(new IntervalMarkdeBerg[leftNodeIntervals.size()]),bufferedWriter);
			
			right = constructIntervalTree(rightNodeIntervals.toArray(new IntervalMarkdeBerg[rightNodeIntervals.size()]),bufferedWriter);
			
			node = new IntervalTreeMarkdeBergNode(
					middleIntervalsLeftEndPointsAscending, 
					middleIntervalsRightEndPointsDescending, 
					median, 
					left, 
					right);
			
		}
		
		return node;
		
	}
	
	

	public IntervalTreeMarkdeBerg(IntervalTreeMarkdeBergNode root) {
		super();
		this.root = root;
	}


	
	public static void traverseIntervalTreeBreadthFirstOrder(IntervalTreeMarkdeBerg intervalTree){
		
		System.out.println("Breadth first tree traversal starts.");
		
		Queue<IntervalTreeMarkdeBergNode> queue = new LinkedList<IntervalTreeMarkdeBergNode>();
		
	    if (intervalTree.getRoot() == null)
	        return;
	    
	    queue.clear();
	    queue.add(intervalTree.getRoot());
	    
	    while(!queue.isEmpty()){
	    	
	        IntervalTreeMarkdeBergNode newNode = queue.remove();
	        System.out.print("Median:" + newNode.getMedian());
	        
	        System.out.print(" Number of node intervals: " + newNode.getIntervalsLeftEndPointsAscending().length);
		      
	        System.out.print(" Node intervals:");
	        Print.printArray(newNode.getIntervalsLeftEndPointsAscending());
	        
	        if(newNode.getLeft() != null) queue.add(newNode.getLeft());
	        if(newNode.getRight() != null) queue.add(newNode.getRight());
	        
	    }//End of while

		System.out.println("Breadth first tree traversal ends.");

	}
	
	
	//8 July 2016 starts
	public static void searchInLeftEndPointsInAscendingOrder(
			String outputFolder,
			String annotationFolder,
			String elementTypeName,
			WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String>fileNumber2FileNameMap,
			Interval interval, 
			ChromosomeName chromName,
			TIntByteMap dnaseCellLineNumber2HeaderWrittenMap,
			TIntByteMap dnaseCellLineNumber2OneorZeroMap,
			IntervalMarkdeBerg[] intervalsLeftEndPointsAscending, 
			int high){
		
		DnaseIntervalMarkdeBerg dnaseIntervalMarkdeBerg = null;
		
		
		for(int i=0; i<intervalsLeftEndPointsAscending.length;i++){
			
			//Case given interval high < median
			//Therefore given interval low <  node's interval's high
			//So we are looking for node's intervals such that their low <= given interval high
			//which means that there is overlap
			if(intervalsLeftEndPointsAscending[i].getLow()<=high){
				
				dnaseIntervalMarkdeBerg = (DnaseIntervalMarkdeBerg)intervalsLeftEndPointsAscending[i];
				
				//There is overlap
				//Write it down.
				writeOverlapsFoundInAnnotation(
						outputFolder,
						annotationFolder,
						elementTypeName,
						writeFoundOverlapsMode,
						cellLineNumber2CellLineNameMap,
						fileNumber2FileNameMap,
						interval, 
						chromName,
						dnaseIntervalMarkdeBerg,
						dnaseCellLineNumber2HeaderWrittenMap);
				
				
					if( !dnaseCellLineNumber2OneorZeroMap.containsKey(dnaseIntervalMarkdeBerg.getCellLineNumber())){
						dnaseCellLineNumber2OneorZeroMap.put(dnaseIntervalMarkdeBerg.getCellLineNumber(), Commons.BYTE_1);
					}
			}//End of IF
			
			else{
					break;
			}
		}//End of FOR each interval in the node

	}
	//8 July 2016 ends

	
	
	public static void searchInLeftEndPointsInAscendingOrder(IntervalMarkdeBerg[] leftEndPointsAscending, int high, BufferedWriter bufferedWriter){
		try {

			for(int i=0; i<leftEndPointsAscending.length;i++){
				if(leftEndPointsAscending[i].getLow()<=high){
					//There is overlap
					//Write it down.
					bufferedWriter.write("Found Overlap: [" + leftEndPointsAscending[i].getLow() + "," + leftEndPointsAscending[i].getHigh() +"]" + System.getProperty("line.separator"));
					}//End of IF
				else{
					break;
				}
			}//End of FOR
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//8 July 2016 starts
	public static void searchInRightEndPointsInDescendingOrder(
			String outputFolder,
			String annotationFolder,
			String elementTypeName,
			WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String>fileNumber2FileNameMap,
			Interval interval, 
			ChromosomeName chromName,
			TIntByteMap dnaseCellLineNumber2HeaderWrittenMap,
			TIntByteMap dnaseCellLineNumber2OneorZeroMap,
			IntervalMarkdeBerg[] intervalsRightEndPointsDescending, 
			int low){
		
		
		DnaseIntervalMarkdeBerg dnaseIntervalMarkdeBerg = null;
		
			
		for(int i=0; i<intervalsRightEndPointsDescending.length;i++){
			
			//Case median < given interval low
			//Therefore node's interval's low  < given interval high  
			//So we are looking for node's intervals such that given interval low <= node's interval's high
			//which means that there is overlap
			if(low <= intervalsRightEndPointsDescending[i].getHigh()){
				//There is overlap
				//Write it down.
				dnaseIntervalMarkdeBerg = (DnaseIntervalMarkdeBerg)intervalsRightEndPointsDescending[i];
				
				//There is overlap
				//Write it down.
				writeOverlapsFoundInAnnotation(
						outputFolder,
						annotationFolder,
						elementTypeName,
						writeFoundOverlapsMode,
						cellLineNumber2CellLineNameMap,
						fileNumber2FileNameMap,
						interval, 
						chromName,
						dnaseIntervalMarkdeBerg,
						dnaseCellLineNumber2HeaderWrittenMap);
				
				
					if( !dnaseCellLineNumber2OneorZeroMap.containsKey(dnaseIntervalMarkdeBerg.getCellLineNumber())){
						dnaseCellLineNumber2OneorZeroMap.put(dnaseIntervalMarkdeBerg.getCellLineNumber(), Commons.BYTE_1);
					}
			
			}//End of IF
			else{
				break;			
			}
		}//End of FOR
	
	}
	//8 July 2016 ends
	
	public static void searchInRightEndPointsInDescendingOrder(IntervalMarkdeBerg[] rightEndPointsDescending, int low, BufferedWriter bufferedWriter){
		try {
			
			for(int i=0; i<rightEndPointsDescending.length;i++){
				
				if(low <= rightEndPointsDescending[i].getHigh()){
					//There is overlap
					//Write it down.
					bufferedWriter.write("Found Overlap: [" + rightEndPointsDescending[i].getLow() + "," + rightEndPointsDescending[i].getHigh() + "]"+ System.getProperty("line.separator"));
				}//End of IF
				else{
					break;			
				}
			}//End of FOR
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void processOverlaps(
			String outputFolder,
			String annotationFolder,
			String elementTypeName,
			WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String>fileNumber2FileNameMap,
			Interval interval, 
			ChromosomeName chromName,
			IntervalTreeMarkdeBergNode node,
			TIntByteMap dnaseCellLineNumber2HeaderWrittenMap,
			TIntByteMap dnaseCellLineNumber2OneorZeroMap){
		
		DnaseIntervalMarkdeBerg dnaseIntervalMarkdeBerg = null;
		
		// We know that all the intervals of node overlaps with given interval
		for(int i=0; i<node.getIntervalsLeftEndPointsAscending().length; i++){
			
			dnaseIntervalMarkdeBerg = (DnaseIntervalMarkdeBerg) node.getIntervalsLeftEndPointsAscending()[i];
			
			writeOverlapsFoundInAnnotation(
					outputFolder,
					annotationFolder,
					elementTypeName,
					writeFoundOverlapsMode,
					cellLineNumber2CellLineNameMap,
					fileNumber2FileNameMap,
					interval, 
					chromName,
					dnaseIntervalMarkdeBerg,
					dnaseCellLineNumber2HeaderWrittenMap);
			
			
			if( !dnaseCellLineNumber2OneorZeroMap.containsKey(dnaseIntervalMarkdeBerg.getCellLineNumber())){
				dnaseCellLineNumber2OneorZeroMap.put(dnaseIntervalMarkdeBerg.getCellLineNumber(), Commons.BYTE_1);
			}
			
		}//End of for each interval
		
	}

	
	// 8 July 2016
	// For testing purposes
	// Dnase
	public static void writeOverlapsFoundInAnnotation(
			String outputFolder,
			String annotationFolder,
			String elementTypeName,
			WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap,
			TIntObjectMap<String>fileNumber2FileNameMap,
			Interval interval, 
			ChromosomeName chromName,
			DnaseIntervalMarkdeBerg dnaseIntervalMarkdeBerg,
			TIntByteMap dnaseCellLineNumber2HeaderWrittenMap){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		String cellLineName = null;
		String fileName = null;

		try {
			

			/**************************************************************************************/
			/**************************WRITE ELEMENT BASED starts**********************************/
			/**************************************************************************************/
			if( writeFoundOverlapsMode.isWriteFoundOverlapsElementBased()){
	
				cellLineName = cellLineNumber2CellLineNameMap.get(dnaseIntervalMarkdeBerg.getCellLineNumber());
				fileName = fileNumber2FileNameMap.get(dnaseIntervalMarkdeBerg.getFileNumber());
	
				fileWriter = FileOperations.createFileWriter(outputFolder + annotationFolder + cellLineName + ".txt", true);
				bufferedWriter = new BufferedWriter(fileWriter);
					 
				//Write header only once for each DNase cellLine
				if (!dnaseCellLineNumber2HeaderWrittenMap.containsKey(dnaseIntervalMarkdeBerg.getCellLineNumber())){
					dnaseCellLineNumber2HeaderWrittenMap.put(dnaseIntervalMarkdeBerg.getCellLineNumber(),Commons.BYTE_1);
	
					bufferedWriter.write("#Searched for Chr" + "\t" + "Given Interval Low" + "\t" +
							 "Given Interval High" + "\t" + "DNase Chr" + "\t" + "DNase Interval Low" + "\t" +
							 "DNase Interval High" + "\t" + "CellLineName" + "\t" + "FileName" +
							 System.getProperty("line.separator"));
				}
				
				//Write each overlap
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" +
						ChromosomeName.convertEnumtoString(chromName) + "\t" + dnaseIntervalMarkdeBerg.getLow() + "\t" + dnaseIntervalMarkdeBerg.getHigh() + "\t" + 
						cellLineName + "\t" + fileName + System.getProperty( "line.separator"));
	
				bufferedWriter.close();
	
			}// End of IF Element Based
			/**************************************************************************************/
			/**************************WRITE ELEMENT BASED ends************************************/
			/**************************************************************************************/
	
			
			/**************************************************************************************/
			/**************************WRITE ELEMENT TYPE BASED starts*****************************/
			/**************************************************************************************/
			else if( writeFoundOverlapsMode.isWriteFoundOverlapsElementTypeBased()){
				
				cellLineName = cellLineNumber2CellLineNameMap.get(dnaseIntervalMarkdeBerg.getCellLineNumber());
				fileName = fileNumber2FileNameMap.get(dnaseIntervalMarkdeBerg.getFileNumber());
	
				fileWriter = FileOperations.createFileWriter(outputFolder + annotationFolder + elementTypeName  + ".txt", true);
				bufferedWriter = new BufferedWriter( fileWriter);
			
				//Write header only once for each DNase cellLine
				if (!dnaseCellLineNumber2HeaderWrittenMap.containsKey(Commons.ONE)){
					dnaseCellLineNumber2HeaderWrittenMap.put(Commons.ONE,Commons.BYTE_1);
	
					bufferedWriter.write("#Searched for Chr" + "\t" + "Given Interval Low" + "\t" +
							 "Given Interval High" + "\t" + "DNase Chr" + "\t" + "DNase Interval Low" + "\t" +
							 "DNase Interval High" + "\t" + "CellLineName" + "\t" + "FileName" +
							 System.getProperty("line.separator"));
				}
				
				//Write each overlap
				bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" +
						ChromosomeName.convertEnumtoString(chromName) + "\t" + dnaseIntervalMarkdeBerg.getLow() + "\t" + dnaseIntervalMarkdeBerg.getHigh() + "\t" + 
						cellLineName + "\t" + fileName + System.getProperty( "line.separator"));
	
				bufferedWriter.close();
				
			}// End of IF Element Type Based
			/**************************************************************************************/
			/**************************WRITE ELEMENT TYPE BASED ends*******************************/
			/**************************************************************************************/

		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	//For testing purposes
	//Dnase
	//EOO
	//IntervalTreeMarkdeBerg
	//TODO has to be changed accordingly
	public static void searchIntervalTreeMarkdeBerg(
			String outputFolder,
			WriteAnnotationFoundOverlapsMode writeFoundOverlapsMode,
			TIntByteMap dnaseCellLineNumber2HeaderWrittenMap,
			TIntObjectMap<String> cellLineNumber2CellLineNameMap, 
			TIntObjectMap<String> fileNumber2FileNameMap,
			IntervalTreeMarkdeBergNode node, 
			Interval interval, 
			ChromosomeName chromName,
			TIntByteMap dnaseCellLineNumber2OneorZeroMap, 
			int overlapDefinition) {
		
		//new code starts
		if (node!=null){
			
			//Case 1
			//Look at node's intervals left end points in ascendig order
			//Look at node's left child
			if (interval.getHigh() <= node.getMedian().intValue()){
				
				//Overlaps with median
				if (interval.getHigh() == node.getMedian().intValue()){
					
					//There are overlaps
					//We know that interval overlaps with node's intervals
					//Write node's intervals down
					processOverlaps(
							outputFolder,
							Commons.DNASE_ANNOTATION_DIRECTORY,
							Commons.DNASE,
							writeFoundOverlapsMode,
							cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap,
							interval, 
							chromName,
							node,
							dnaseCellLineNumber2HeaderWrittenMap,
							dnaseCellLineNumber2OneorZeroMap);
				}
				else{
					//Search in
					//Overlaps if node.getIntervalsLeftEndPointsAscending()[i].getLow() <= interval.getHigh()  
					//else breaks
					searchInLeftEndPointsInAscendingOrder(
							outputFolder,
							Commons.DNASE_ANNOTATION_DIRECTORY,
							Commons.DNASE,
							writeFoundOverlapsMode,
							cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap,
							interval, 
							chromName,
							dnaseCellLineNumber2HeaderWrittenMap,
							dnaseCellLineNumber2OneorZeroMap,
							node.getIntervalsLeftEndPointsAscending(),
							interval.getHigh());

				}
				
				//Continue search in left node
				searchIntervalTreeMarkdeBerg(
						outputFolder,
						writeFoundOverlapsMode,
						dnaseCellLineNumber2HeaderWrittenMap,
						cellLineNumber2CellLineNameMap, 
						fileNumber2FileNameMap,
						node.getLeft(), 
						interval, 
						chromName,
						dnaseCellLineNumber2OneorZeroMap, 
						overlapDefinition);	
			}
			
			//Case 2 
			//Look at node's intervals right end points in descendig order
			//Look at node's right child
			else if (node.getMedian().intValue() <= interval.getLow()){
				
				//Overlaps with median
				if (node.getMedian().intValue() == interval.getLow()){
					
					//There are overlaps
					//We know that interval overlaps with node's intervals
					//Write node's intervals down
					processOverlaps(
							outputFolder,
							Commons.DNASE_ANNOTATION_DIRECTORY,
							Commons.DNASE,
							writeFoundOverlapsMode,
							cellLineNumber2CellLineNameMap,
							fileNumber2FileNameMap,
							interval, 
							chromName,
							node,
							dnaseCellLineNumber2HeaderWrittenMap,
							dnaseCellLineNumber2OneorZeroMap);

				}else{
					
					//Search in
					//Overlaps if interval.getLow() <=  node.getIntervalsRightEndPointsDescending()[i].getHigh()
					//else breaks
					searchInRightEndPointsInDescendingOrder(
							 outputFolder,
							 Commons.DNASE_ANNOTATION_DIRECTORY,
							 Commons.DNASE,
							 writeFoundOverlapsMode,
							 cellLineNumber2CellLineNameMap,
							 fileNumber2FileNameMap,
							 interval, 
							 chromName,
							 dnaseCellLineNumber2HeaderWrittenMap,
							 dnaseCellLineNumber2OneorZeroMap,
							 node.getIntervalsRightEndPointsDescending(), 
							 interval.getLow());	

				}
				
					
				//Continue search in right node
				searchIntervalTreeMarkdeBerg(
						outputFolder,
						writeFoundOverlapsMode,
						dnaseCellLineNumber2HeaderWrittenMap,
						cellLineNumber2CellLineNameMap, 
						fileNumber2FileNameMap,
						node.getRight(), 
						interval, 
						chromName,
						dnaseCellLineNumber2OneorZeroMap, 
						overlapDefinition);	
			}
			

			//Case3
			else if (interval.getLow() <= node.getMedian().intValue() && node.getMedian().intValue() <=interval.getHigh()){
				
				//There are overlaps
				//We know that interval overlaps with node's intervals
				//Write node's intervals down
				processOverlaps(
						outputFolder,
						Commons.DNASE_ANNOTATION_DIRECTORY,
						Commons.DNASE,
						writeFoundOverlapsMode,
						cellLineNumber2CellLineNameMap,
						fileNumber2FileNameMap,
						interval, 
						chromName,
						node,
						dnaseCellLineNumber2HeaderWrittenMap,
						dnaseCellLineNumber2OneorZeroMap);
				
				//Continue search in left node
				searchIntervalTreeMarkdeBerg(
						outputFolder,
						writeFoundOverlapsMode,
						dnaseCellLineNumber2HeaderWrittenMap,
						cellLineNumber2CellLineNameMap, 
						fileNumber2FileNameMap,
						node.getLeft(), 
						interval, 
						chromName,
						dnaseCellLineNumber2OneorZeroMap, 
						overlapDefinition);	
	
				//Continue search in right node
				searchIntervalTreeMarkdeBerg(
						outputFolder,
						writeFoundOverlapsMode,
						dnaseCellLineNumber2HeaderWrittenMap,
						cellLineNumber2CellLineNameMap, 
						fileNumber2FileNameMap,
						node.getRight(), 
						interval, 
						chromName,
						dnaseCellLineNumber2OneorZeroMap, 
						overlapDefinition);	

			}

			//Control Case
			else {
				System.out.println("Security there is a problem. There is an unhandled case." + System.getProperty("line.separator"));
			}
			
		}//End of IF node is not null
		//new code ends
		

				
	}//End of search method

	
	public static void searchIntervalTreeMarkdeBerg(
			IntervalTreeMarkdeBergNode node, 
			Interval interval, 
			BufferedWriter searchOutputBufferedWriter) throws IOException{
		
		if (node!=null){
			
			//Case 1
			//Look at node's intervals left end points in ascendig order
			//Look at node's left child
			if (interval.getHigh() <= node.getMedian()){
				
				//In case of equality
				if (interval.getHigh() == node.getMedian()){
					
					//We know that interval overlaps with node's intervals
					//Write node's intervals down
					searchOutputBufferedWriter.write("Found overlaps: ");
					Print.printArray(node.getIntervalsLeftEndPointsAscending(),searchOutputBufferedWriter);
					searchOutputBufferedWriter.write(System.getProperty("line.separator"));
					
				}else{
					//Search in
					//Overlaps if node.getIntervalsLeftEndPointsAscending()[i].getLow() <= interval.getHigh()  
					//else breaks
					searchInLeftEndPointsInAscendingOrder(node.getIntervalsLeftEndPointsAscending(),interval.getHigh(),searchOutputBufferedWriter);
				}
				
				//Continue search in left node
				searchIntervalTreeMarkdeBerg(node.getLeft(), interval,searchOutputBufferedWriter);	
			}
			
			//Case 2 
			//Look at node's intervals right end points in descendig order
			//Look at node's right child
			else if (node.getMedian() <= interval.getLow()){
				
				//In case of equality
				if (node.getMedian() == interval.getLow()){
					//We know that interval overlaps with node's intervals
					//Write node's intervals down
					searchOutputBufferedWriter.write("Found overlaps: ");
					Print.printArray(node.getIntervalsLeftEndPointsAscending(),searchOutputBufferedWriter);
					searchOutputBufferedWriter.write(System.getProperty("line.separator"));
					
				}else{
					//Search in
					//Overlaps if interval.getLow() <=  node.getIntervalsRightEndPointsDescending()[i].getHigh()
					//else breaks
					searchInRightEndPointsInDescendingOrder(node.getIntervalsRightEndPointsDescending(),interval.getLow(),searchOutputBufferedWriter);	
				}
				
				//Continue search in right node
				searchIntervalTreeMarkdeBerg(node.getRight(), interval,searchOutputBufferedWriter);	
			}
			
			//Case 3
			//Look at node's left child
			//Look at node's right child
			else if (interval.getLow() <= node.median && node.median <=interval.getHigh()){
				
				//We know that interval overlaps with node's intervals
				//Write node's intervals down
				searchOutputBufferedWriter.write("Found overlaps: ");
				Print.printArray(node.getIntervalsLeftEndPointsAscending(),searchOutputBufferedWriter);
				searchOutputBufferedWriter.write(System.getProperty("line.separator"));

				searchIntervalTreeMarkdeBerg(node.getLeft(), interval,searchOutputBufferedWriter);	
				searchIntervalTreeMarkdeBerg(node.getRight(), interval,searchOutputBufferedWriter);	
				
			}
			
			//Control Case
			else {
				searchOutputBufferedWriter.write("Security there is a problem. There is an unhandled case." + System.getProperty("line.separator"));
			}
			
		}//End of IF node is not null
		
	}

	
	public static void traverseIntervalTreeBreadthFirstOrder(IntervalTreeMarkdeBerg intervalTree, BufferedWriter bufferedWriter){
		
		try {
			bufferedWriter.write("Breadth first tree traversal starts." + System.getProperty("line.separator"));
		
			Queue<IntervalTreeMarkdeBergNode> queue = new LinkedList<IntervalTreeMarkdeBergNode>();
			
		    if (intervalTree.getRoot() == null)
		        return;
		    
		    queue.clear();
		    queue.add(intervalTree.getRoot());
		    
		    while(!queue.isEmpty()){
		    	
		        IntervalTreeMarkdeBergNode newNode = queue.remove();
		        bufferedWriter.write("Median:" + newNode.getMedian());
		        
		        bufferedWriter.write(" Number of node intervals: " + newNode.getIntervalsLeftEndPointsAscending().length);
			      
		        bufferedWriter.write(" Node intervals:");
		        Print.printArray(newNode.getIntervalsLeftEndPointsAscending(),bufferedWriter);
		        
		        if(newNode.getLeft() != null) queue.add(newNode.getLeft());
		        if(newNode.getRight() != null) queue.add(newNode.getRight());
		        
		    }//End of while

		    bufferedWriter.write("Breadth first tree traversal ends."+ System.getProperty("line.separator"));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
			
			String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];
			String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");
			String outputFolder = glanetFolder + Commons.OUTPUT + System.getProperty( "file.separator");
			
			IntervalMarkdeBerg i1 = new IntervalMarkdeBerg(10, 20);
			IntervalMarkdeBerg i2 = new IntervalMarkdeBerg(5, 40);
			IntervalMarkdeBerg i3 = new IntervalMarkdeBerg(30, 50);
			IntervalMarkdeBerg i4 = new IntervalMarkdeBerg(0, 15);
			IntervalMarkdeBerg i5 = new IntervalMarkdeBerg(40, 60);
			IntervalMarkdeBerg i6 = new IntervalMarkdeBerg(10, 30);
			IntervalMarkdeBerg i7 = new IntervalMarkdeBerg(70, 80);
			IntervalMarkdeBerg i8 = new IntervalMarkdeBerg(80, 100);
			IntervalMarkdeBerg i9 = new IntervalMarkdeBerg(0, 100);
			IntervalMarkdeBerg i10 = new IntervalMarkdeBerg(80, 120);
			IntervalMarkdeBerg i11 = new IntervalMarkdeBerg(90, 100);
			
			List<IntervalMarkdeBerg> list = new ArrayList<IntervalMarkdeBerg>();
			
			list.add(i1);
			list.add(i2);
			list.add(i3);
			list.add(i4);
			list.add(i5);
			list.add(i6);
			list.add(i7);
			list.add(i8);
			list.add(i9);
			list.add(i10);
			list.add(i11);
	
			/**************************************************************************************/
			/********************TIME MEASUREMENT**************************************************/
			/**************************************************************************************/
			// if you want to see the current year and day etc. change the line of code below with:
			// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			long dateBefore = Long.MIN_VALUE;
			long dateAfter = Long.MIN_VALUE;
			/**************************************************************************************/
			/********************TIME MEASUREMENT**************************************************/
			/**************************************************************************************/
	
			//Time before constructing interval tree 
			dateBefore = System.currentTimeMillis();
			
			BufferedWriter bufferedWriter = null;
			
			FileWriter searchFileWriter = null;
			BufferedWriter searchBufferedWriter = null;
			
			
			searchFileWriter = FileOperations.createFileWriter(outputFolder + "SearchOutputIntervalTreeMarkdeBerg.txt");
			searchBufferedWriter = new BufferedWriter(searchFileWriter);
		
			IntervalMarkdeBerg[] intervalArrayUnsorted = (IntervalMarkdeBerg[]) list.toArray(new IntervalMarkdeBerg[list.size()]);
			IntervalMarkdeBerg[] intervalArraySorted = new IntervalMarkdeBerg[list.size()];
			
			//Do it only once. Sort intervals in ascending order w.r.t. left end points.
			CountingSorting.sortLeftEndPointsAscending(intervalArrayUnsorted, SortingOrder.SORTING_IN_ASCENDING_ORDER, intervalArraySorted);			
			
			//Free space
			list = null;
		
			//You have a list of intervals
			IntervalTreeMarkdeBergNode root = constructIntervalTree(intervalArraySorted,bufferedWriter);
			
			IntervalTreeMarkdeBerg intervalTree = new IntervalTreeMarkdeBerg(root);
			
			//Time after constructing interval tree 
			dateAfter = System.currentTimeMillis();
	
			//How much did it take to construct interval tree using Mark de Berg?
			System.out.println("Construction Time for Interval Tree Mark de Berg: " + ((dateAfter - dateBefore)*1.0f)/1000 + " seconds");
			System.out.println("Construction Time for Interval Tree Mark de Berg: " + (dateAfter - dateBefore) + " milli seconds");
			System.out.println("****************************************************************");
	
			if (root!=null){
				traverseIntervalTreeBreadthFirstOrder(intervalTree);
			}
			
			Interval givenInterval = new Interval(96,96);
			searchIntervalTreeMarkdeBerg(intervalTree.getRoot(), givenInterval, searchBufferedWriter);
				
			//Close bufferedWriter
			//bufferedWriter.close();
			searchBufferedWriter.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
