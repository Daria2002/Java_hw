package hr.fer.zemris.java.hw06.crypto;

/**
 * This class converts from hex String to byte[] and from byte[] to String
 * @author Daria Matković
 *
 */
public class Util {

	public static byte[] hextobyte(String hex) {
		int len = hex.length();
	    byte[] data = new byte[len / 2];
	    
	    for (int i = 0; i < len; i += 2) {
	    	char currentChar = hex.charAt(i);
	    	char nextChar = hex.charAt(i+1);
	    	
	    	checkHex(currentChar);
	    	checkHex(nextChar);
	    	
	        data[i / 2] = (byte) ((Character.digit(currentChar, 16) << 4)
	                             + Character.digit(hex.charAt(i+1), 16));
	    }
	    return data;
	}
	
	private static void checkHex(char value) {
		String valueString = String.valueOf(value).toLowerCase();
		
		if(!Character.isDigit(value) && !("a".equals(valueString) ||
				"b".equals(valueString) || "c".equals(valueString) ||
				"d".equals(valueString) || "e".equals(valueString) ||
				"f".equals(valueString))) {
			throw new IllegalArgumentException("Invalid hex value");
		}
	}

	public static String bytetohex(byte[] byteArray) {
		String result = "";

		for (int i = 0; i < byteArray.length; i++) {
			result += Integer.toString( ( byteArray[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		
		return result;
	}
	
}
