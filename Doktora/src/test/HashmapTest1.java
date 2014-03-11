package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HashmapTest1 {
	
 HashMap<String,List<Integer>> hashmap = new HashMap<String,List<Integer>>();
	
	public void traverseHashtable(){
		
		Iterator<Entry<String,List<Integer>>> itr = hashmap.entrySet().iterator();
		
		while(itr.hasNext()){
			
			Entry<String,List<Integer>> entry = (Entry<String,List<Integer>>)itr.next();
			System.out.print(entry.getKey() + " "); 
			List<Integer> list = entry.getValue();
			
			Iterator<Integer> itr2 = list.iterator();
			
			while(itr2.hasNext()){
				Integer i =(Integer)itr2.next();
				System.out.print(i+" "); 				
			}
			System.out.print("\n"); 
			
		}
	}
	
	
	public void looptheHashMap() {
		// more elegant way
		for (Map.Entry<String, List<Integer>> entry : hashmap.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : "
				+ entry.getValue());
		}	
	}
	
	
	public void fillHashtable(){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(50);
		
		hashmap.put("burcak",list);
		
		list = new ArrayList<Integer>();
		list.add(100);
		list.add(200);
		hashmap.put("afsin", list);
		
		list = new ArrayList<Integer>();
		list.add(3000);
		list.add(4000);
		hashmap.put("betul", list);
				
		list = new ArrayList<Integer>();
		list.add(50000);
		list.add(60000);
		hashmap.put("ediz", list);
				
	}
	
	public Map<Integer,List<Integer>> mergeMaps(Map<Integer,List<Integer>> leftMap, Map<Integer,List<Integer>> rightMap){
		
		for(Map.Entry<Integer,List<Integer>> entry: leftMap.entrySet()){
			rightMap.put(entry.getKey(), entry.getValue());
		}
		
		leftMap.clear();
		leftMap = null;
		
		return rightMap;
	}
	
	public static void main(String[] args){
		HashmapTest1 hashmapTest = new HashmapTest1();
		hashmapTest.fillHashtable();
		hashmapTest.traverseHashtable();
		hashmapTest.looptheHashMap();
		
		Map<Integer,List<Integer>> leftMap = new HashMap<Integer,List<Integer>> ();
		Map<Integer,List<Integer>> rightMap = new HashMap<Integer,List<Integer>> ();
		
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(10);
		list2.add(20);
		list2.add(30);
		list2.add(40);
		
		leftMap.put(1, list1);
		rightMap.put(2, list2);
		
		hashmapTest.mergeMaps(leftMap,rightMap);
		
		leftMap = null;
		
		System.out.println("What does happens?");
		
		
	}

}
