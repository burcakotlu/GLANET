/**
 * @author Burcak Otlu
 * Aug 18, 2013
 * 1:32:16 PM
 * 2013
 *
 * This class reads a given wig file
 * And created chromosome based mapability files.
 */
package mapability;

import enumtypes.ChromosomeName;
import enumtypes.CommandLineArguments;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import ui.GlanetRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;
import auxiliary.FileOperations;
import common.Commons;

public class CreationofChromosomeBasedMapabilityFilesFromWigFiles {

	final static Logger logger = Logger.getLogger(CreationofChromosomeBasedMapabilityFilesFromWigFiles.class);

	public static void readMapabilityFileWriteChromBasedMapabilityFiles( 
			String dataFolder,
			TIntObjectMap<BufferedWriter> chromName2BufferedWriterMap) {

		BufferedReader bufferedReader;

		String strLine;
		int indexofNumber;

		ChromosomeName chromName = null;
		int low;
		int high;
		double mapability;

		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;

		BufferedWriter correspondingBufferedWriter = null;

		try{
			bufferedReader = FileOperations.createBufferedReader( 
					dataFolder,
					Commons.WG_ENCODE_CRG_MAPABILITY_ALIGN_100_MER_WIG);

			while( ( strLine = bufferedReader.readLine()) != null){
				indexofNumber = strLine.indexOf( '#');

				// #bedGraph section chr1:10000-81873 (indexofNumber=0)
				// chr1 10015 10026 0.5 (indexofNumber=-1)

				if( indexofNumber == -1){
					indexofFirstTab = strLine.indexOf( '\t');
					indexofSecondTab = strLine.indexOf( '\t', indexofFirstTab + 1);
					indexofThirdTab = strLine.indexOf( '\t', indexofSecondTab + 1);

					chromName = ChromosomeName.convertStringtoEnum( strLine.substring( 0, indexofFirstTab));
					low = Integer.parseInt( strLine.substring( indexofFirstTab + 1, indexofSecondTab));
					high = Integer.parseInt( strLine.substring( indexofSecondTab + 1, indexofThirdTab));
					mapability = Double.parseDouble( strLine.substring( indexofThirdTab + 1));

					// chrM 0 612 1
					if( chromName != null){
						correspondingBufferedWriter = chromName2BufferedWriterMap.get( chromName.getChromosomeName());
						correspondingBufferedWriter.write( chromName.convertEnumtoString() + "\t" + low + "\t" + high + "\t" + mapability + System.getProperty( "line.separator"));
					}

					if( correspondingBufferedWriter == null){
						if( GlanetRunner.shouldLog())logger.error( "No BufferedWriter is possible for this null chrName");
					}

				}
			}

			bufferedReader.close();

		}catch( FileNotFoundException e){
			e.printStackTrace();
		}catch( IOException e){
			e.printStackTrace();
		}

	}

	public static void createChromBasedBufferedWriters( String dataFolder,
			TIntObjectMap<BufferedWriter> chromName2BufferedWriterMap) {

		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		String chromBasedMapabilityFileName = null;

		try{

			for( ChromosomeName chrName : ChromosomeName.values()){

				chromBasedMapabilityFileName = Commons.MAPPABILITY + System.getProperty( "file.separator") + chrName.convertEnumtoString() + Commons.MAPABILITY_HG19_FILE_END;
				fileWriter = FileOperations.createFileWriter( dataFolder, chromBasedMapabilityFileName);
				bufferedWriter = new BufferedWriter( fileWriter);
				chromName2BufferedWriterMap.put( chrName.getChromosomeName(), bufferedWriter);

			}// End of For

		}catch( IOException e){
			e.printStackTrace();
		}
	}

	public static void closeChromBasedBufferedWriters( TIntObjectMap<BufferedWriter> chromName2BufferedWriterMap) {

		BufferedWriter bufferedWriter;

		for( TIntObjectIterator<BufferedWriter> it = chromName2BufferedWriterMap.iterator(); it.hasNext();){

			it.advance();

			bufferedWriter = it.value();

			// Close bufferedWriter
			try{
				bufferedWriter.close();
			}catch( IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// End of for

	}

	public static void main( String[] args) {

		String glanetFolder = args[CommandLineArguments.GlanetFolder.value()];

		String dataFolder = glanetFolder + Commons.DATA + System.getProperty( "file.separator");

		TIntObjectMap<BufferedWriter> chromName2BufferedWriterMap = new TIntObjectHashMap<BufferedWriter>();
		// Map<ChromosomeName, BufferedWriter> chromName2BufferedWriterHashMap = new HashMap<ChromosomeName,
		// BufferedWriter>();

		createChromBasedBufferedWriters( dataFolder, chromName2BufferedWriterMap);
		readMapabilityFileWriteChromBasedMapabilityFiles( dataFolder, chromName2BufferedWriterMap);
		closeChromBasedBufferedWriters( chromName2BufferedWriterMap);

	}

}
