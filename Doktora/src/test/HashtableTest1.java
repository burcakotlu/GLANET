package test;


public class HashtableTest1 {
	
	Hashtable<String,List> hashtable = new Hashtable<String,List>();
	
	public void traverseHashtable(){
		Enumeration<List> enumeration = hashtable.elements();
		Enumeration enumerationKeys = hashtable.keys();
		
		
		while(enumeration.hasMoreElements() && enumerationKeys.hasMoreElements()) {
			String key = (String)enumerationKeys.nextElement();
			ArrayList<Integer> list = (ArrayList<Integer>) enumeration.nextElement();
			
			System.out.print(key +" ");					
			
			for (int i =0; i<list.size(); i++){
				System.out.print(list.get(i)+" ");					
			}
			System.out.println();					
			
		}
	}
	
	public void fillHashtable(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(50);
		
		hashtable.put("burcak",list);
		
		list = new ArrayList<Integer>();
		list.add(100);
		list.add(200);
		hashtable.put("afsin", list);
		
		list = new ArrayList<Integer>();
		list.add(3000);
		list.add(4000);
		hashtable.put("betul", list);
				
		list = new ArrayList<Integer>();
		list.add(50000);
		list.add(60000);
		hashtable.put("ediz", list);
				
	}
	
	public static void main(String[] args){
		HashtableTest1 hashtableTest = new HashtableTest1();
		hashtableTest.fillHashtable();
		hashtableTest.traverseHashtable();
		
		
	}
}
