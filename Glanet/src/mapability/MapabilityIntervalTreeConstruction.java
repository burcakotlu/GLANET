/**
 * 
 */
package mapability;

import intervaltree.IntervalTree;
import intervaltree.MapabilityIntervalTreeNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;

/**
 * @author Burcak Otlu
 * @date Apr 13, 2015
 * @project Glanet 
 *
 */
public class MapabilityIntervalTreeConstruction {

	public static void fillIntervalTree( String dataFolder, ChromosomeName chromName,
			IntervalTree mapabilityIntervalTree) {

		String mapabilityFileName = Commons.MAPABILITY + System.getProperty( "file.separator") + chromName.convertEnumtoString() + Commons.MAPABILITY_HG19_FILE_END;

		FileReader fileReader;
		BufferedReader bufferedReader;

		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		int lowZeroBased;
		int highOneBased;
		int highZeroBased;
		short mapability = 0;

		MapabilityIntervalTreeNode node = null;

		try{

			fileReader = FileOperations.createFileReader( dataFolder + mapabilityFileName);
			bufferedReader = new BufferedReader( fileReader);

			// example line
			// chrName lowZeroBased highOneBased mapability
			// chr1 10000 10014 0.00277778

			/*****************************************************************/
			while( ( strLine = bufferedReader.readLine()) != null){

				indexofFirstTab = strLine.indexOf( '\t');
				indexofSecondTab = ( indexofFirstTab > 0)?strLine.indexOf( '\t', indexofFirstTab + 1):-1;
				indexofThirdTab = ( indexofSecondTab > 0)?strLine.indexOf( '\t', indexofSecondTab + 1):-1;

				lowZeroBased = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
				highOneBased = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));

				highZeroBased = highOneBased - 1;

				mapability = ( short)( Float.parseFloat( strLine.substring( indexofThirdTab + 1)) * Commons.MAPABILITY_SHORT_TEN_THOUSAND);

				// Create the Interval Tree Node
				// Insert the created node into the interval tree
				// if mapability is greater than 0
				if( mapability > 0){

					// Create new interval tree node
					node = new MapabilityIntervalTreeNode( lowZeroBased, highZeroBased, mapability);

					// Add to the interval tree
					mapabilityIntervalTree.intervalTreeInsert( mapabilityIntervalTree, node);
				}

			}// End of WHILE
			/*****************************************************************/

			// Close bufferedReader
			bufferedReader.close();

		}catch( FileNotFoundException e){
			e.printStackTrace();
		}catch( IOException e){
			e.printStackTrace();
		}

	}

	public static void main( String[] args) {

	}

}
