package hr.fer.zemris.java.hw06.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Program that allows the user to encrypt/decrypt given file using
 * the AES cryptoalgorithm and the 128-bit encryption key or calculate
 * and check the SHA-256 file digest.
 * @author Daria Matković
 *
 */
public class Crypto {

	private static final String CHECK_SHA = "checksha";
	private static final String ENCRYPT = "encrypt";
	private static final String DECRYPT = "decrypt";
	
	/**
	 * This method is executed when program is run.
	 * @param args first argument is operation and second argument is file.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		if(args.length != 2) {
			throw new IllegalArgumentException("Please enter operation and file name");
		}
		
		String operation = args[0];
		String fileName = args[1];
		
		String digest = executeOperation(operation, fileName);
		System.out.println(digest);
	}

	private static String executeOperation(String operation, String fileName) throws Exception {
		
		switch (operation) {
		case CHECK_SHA:
			return getMD5Checksum(fileName);
			/*
		case ENCRYPT:
			return executeEncrypt(fileName);
		
		case DECRYPT:
			return executeDecrypt(fileName);
*/
		default:
			throw new IllegalArgumentException("Entered operation is invalid.");
		}
	}

	
	public static byte[] createChecksum(String filename) throws Exception {
	       InputStream fis =  new FileInputStream(filename);

	       byte[] buffer = new byte[4000];
	       MessageDigest complete = MessageDigest.getInstance("SHA-256");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	   }

	   // see this How-to for a faster way to convert
	   // a byte array to a HEX string
	   public static String getMD5Checksum(String filename) throws Exception {
	       byte[] b = createChecksum("src/main/java/hr/fer/zemris/java/hw06/crypto/"+ filename);
	       String result = "";

	       for (int i=0; i < b.length; i++) {
	           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	       }
	       return result;
	   }
	
	private static byte[] executeCheckSha(String fileName) throws IOException, NoSuchAlgorithmException {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		InputStream is = Files.newInputStream(Paths.get("src/main/java/hr/fer/zemris/java/hw06/crypto/"+fileName));
		DigestInputStream dis = new DigestInputStream(is, sha);
		byte[] digest = sha.digest();
		return digest;
	}

	private static byte[] executeEncrypt(String fileName) throws NoSuchAlgorithmException {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		byte[] digest = sha.digest();
		return digest;
	}

	private static byte[] executeDecrypt(String fileName) throws NoSuchAlgorithmException {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		byte[] digest = sha.digest();
		return digest;
	}
}
