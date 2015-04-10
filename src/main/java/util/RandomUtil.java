package util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

/**Does not create a static class as you must utilize a single seed in subsequent
 * calls to insure unique randomness
 * 
 */
public class RandomUtil {
    private static Random hdrRndm = new Random();

	/**Returns a string of randomly generate alpha numeric characters. This string
	 * will be the length specified by the input parameter. 
	 * @param length the total length of random characters you wish returned
	 * @return <b>string</b> a randomly generated string of alphanumeric characters only
	 */
    public RandomUtil() {
    	hdrRndm.setSeed(System.currentTimeMillis());
    }
    
    public static Integer generateRandomNumber() {
    	return RandomUtils.nextInt();
    }
    
    public static int getRandomNumberInRange(int low, int high) {
    	return hdrRndm.nextInt(high-low) + low;
    }
    
   

    public static int generateRandom(int length){
    	int value = hdrRndm.nextInt(length);
		return value;
    }
    
    public static BigDecimal generateRandomDecimal(int Length)throws Exception{
    	if(Length == 0){
    		return null;
    	}
    	String sNums = generateRandomNumericString(Length);
    	String sNums2 = generateRandomNumericString(2);
    	sNums = sNums + "." + sNums2;
    	return new BigDecimal(sNums);
    }
    
    public static Long generateRandomNumeric(int Length)throws Exception{
    	if(Length == 0){
    		return null;
    	}
    	String sNums = generateRandomNumericString(Length);
    	return new Long(sNums);
    }
    public static String generateRandomAlphaString(int StringLength)throws Exception{
    	if(StringLength == 0){
    		return null;
    	}
    	StringBuffer returnVal = new StringBuffer();
    	String[] vals = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
    			"n","o","p","q","r","s","t","u","v","w","x","y","z"};
    	for(int lp = 0;lp < StringLength; lp++){
    		returnVal.append(vals[generateRandom(26)]);
    	}
    	return returnVal.toString();
    }
    public static String generateRandomText(int StringLength)throws Exception{
    	if(StringLength == 0){
    		return null;
    	}
    	StringBuffer returnVal = new StringBuffer();
    	String[] vals = {"a","b","c","d","e","f","g","h","i","j",
    			"k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    	for(int lp = 0;lp < StringLength; lp++){
    		returnVal.append(vals[generateRandom(30)]);
    	}
    	return returnVal.toString();
    }
    public static String generateRandomNumericString(int StringLength)throws Exception{
    	if(StringLength == 0){
    		return null;
    	}
    	StringBuffer returnVal = new StringBuffer();
    	String[] vals = {"1","2","3","4","5","6","7","8","9"};
    	for(int lp = 0;lp < StringLength; lp++){
    		returnVal.append(vals[generateRandom(10)]);
    	}
    	return returnVal.toString();
    }
    
    public static Integer generateRandomNumbers(int Length)throws Exception{
    	if(Length == 0){
    		return null;
    	}
    	String sNums = generateRandomNumericString(Length);
    	return new Integer(sNums);
    }
    
    public static Object getRandomObjectFromList(List objList)throws Exception{
    	if(objList == null || objList.size() == 0){
    		return null;
    	}else if(objList.size() == 1){
    		return objList.get(0);
    	}
    	int lGetObjIndex = generateRandom(objList.size() - 1);
    	return (Object) objList.get(lGetObjIndex);
    }
    public static String getAlphaNumericRandom(int length) throws Exception{
		Random generator = new Random();
		String[] mapOfCharacters = getCharacterMap();
		StringBuffer sRandomString = new StringBuffer();

		// Now lets return the number of characters requested
		for (int j = 0; j < length; j++) {
			int rndm = generator.nextInt(61) + 0;
			String sItem = mapOfCharacters[rndm];
			sRandomString.append(sItem);
		}
		return sRandomString.toString();
	}

	private static String[] getCharacterMap() throws Exception{
		String[] universeValues = new String[62];
		int asciiAlpha = 65; // The start of the alpha ascii character set

		// Add the numbers
		for (int i = 0; i < 62; i++) {
			if (i < 10) {
				// numbers zero through 9
				universeValues[i] = new Integer(i).toString();
			} else {
				universeValues[i] = Character.toString((char) asciiAlpha);
				// 91 - 96 are not alpha characters in the ascii map
				if (asciiAlpha + 1 == 91) {
					asciiAlpha = 97;
				} else {
					asciiAlpha = asciiAlpha + 1;
				}
			}
		}
		return universeValues;
	}
}
