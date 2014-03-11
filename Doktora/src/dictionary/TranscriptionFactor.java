package dictionary;

import java.util.HashMap;
import java.util.Map;

public class TranscriptionFactor {
	
	public static final Map<String,String> tfDictionary;
	static{
		tfDictionary= new HashMap<String,String>();
		tfDictionary.put("NFKB", "NFKB");
		tfDictionary.put("NFE2", "NFE2");
		tfDictionary.put("MAX", "MAX");
		tfDictionary.put("CMYC", "CMYC");
		tfDictionary.put("GATA2", "GATA2");
		tfDictionary.put("SREBP1", "SREBP1");
		tfDictionary.put("SREBP2", "SREBP2");
		tfDictionary.put("CJUN", "CJUN");
		tfDictionary.put("NRSF", "NRSF");
		tfDictionary.put("TBP", "TBP");
	}
	
	

	public TranscriptionFactor() {
		
	}

	

}
