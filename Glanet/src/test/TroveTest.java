/**
 * @author burcakotlu
 * @date May 15, 2014 
 * @time 3:45:41 PM
 */
package test;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * 
 */
public class TroveTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TIntObjectHashMap tIntIntMap = new TIntObjectHashMap();
		tIntIntMap.put(100, 10000);
		tIntIntMap.put(200, 20000);
		tIntIntMap.put(20, 200);
			
		TIntList tIntList = new TIntArrayList();
		tIntList.add(1);
		tIntList.add(12);
		tIntList.add(13);
		
		//MAP
		// accessing keys/values through an iterator:
		for ( TIntObjectIterator it = tIntIntMap.iterator(); it.hasNext(); ) {
		    it.advance();
		    System.out.println(it.key() + "\t" + it.value() );
		}
		
		//LIST
		for ( TIntIterator it2 = tIntList.iterator(); it2.hasNext(); ) {
		    System.out.println(it2.next() + "," );	    
		}
		
		System.out.println("");
		
	}

}
