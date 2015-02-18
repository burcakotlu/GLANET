package binomialdistribution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import common.Commons;

public class CalculateUtility {

	public static void fillHashMapwithZeros(Map<String, Long> hashMap, String inputFileName) {
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);

			while ((strLine = bufferedReader.readLine()) != null) {
				if (!(hashMap.containsKey(strLine))) {
					// hashMap.put(strLine.toUpperCase(Locale.ENGLISH),
					// Commons.LONG_ZERO);
					hashMap.put(strLine, Commons.LONG_ZERO);
				}

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
