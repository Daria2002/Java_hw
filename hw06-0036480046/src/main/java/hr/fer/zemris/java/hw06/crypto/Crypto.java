package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
	
	private static final String PATH_TO_FOLDER = "src/main/java/hr/fer/zemris/java/hw06/crypto/";
	
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
		
		String result = executeOperation(operation, fileName);
		System.out.println(result);
	}

	/**
	 * Checks given operation and executes.
	 * @param operation operation to execute
	 * @param fileName file for operation.
	 * @return String that represents result message to user.
	 * @throws Exception throws exception if error occurs.
	 */
	private static String executeOperation(String operation, String fileName) throws Exception {

		Scanner scanner = new Scanner(System.in);
		
		switch (operation) {
		case CHECK_SHA:
			String digestExpected = getDigest(fileName);
			String digestToCheck = getDigestToCheck(fileName, scanner);
			
			scanner.close();
			
			String message = digestExpected.equals(digestToCheck) ?
					" matches expected digest." : 
					" does not match the expected digest. Digest\n" + "was: " +
					digestExpected;
			
			return "Digesting completed. Digest of " + fileName + message;
			
		case ENCRYPT:
			String keyText = getPassword(scanner);
			String ivText = getIniVector(scanner);
			
			scanner.close();
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
			
			Path source = Paths.get(PATH_TO_FOLDER + fileName);
			InputStream in = new BufferedInputStream(Files.newInputStream(source),
		            4000);

			String newFileName = getNewFileName(fileName);		
			File destinationFile = new File(PATH_TO_FOLDER + newFileName);
			destinationFile.mkdirs();
			destinationFile.createNewFile();
			FileOutputStream destinationFileStream = new FileOutputStream(destinationFile, false); 
			Path destination = Paths.get(PATH_TO_FOLDER + newFileName);
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(
            		destination), 4000);
            
            int i = 0;
            while(i<5) {
            	i++;
            	byte[] b  = cipher.update(in.readNBytes(4000));
            	out.write(b);
            }
            out.write(cipher.doFinal());
            
            return "";
            
			//return executeEncrypt(fileName);
		/*
		case DECRYPT:
			return executeDecrypt(fileName);
		*/
		default:
			throw new IllegalArgumentException("Entered operation is invalid.");
		}
	}

	
	private static String getNewFileName(String fileName) {
		StringBuilder newFileName = new StringBuilder();
		int index = fileName.indexOf('.');
		
		for(int i = 0; i < index; i++) {
			newFileName.append(fileName.charAt(i));
		}
		
		newFileName.append(".crypted.pdf");
		return newFileName.toString();
	}
	
	private static String getIniVector(Scanner scanner) {
		String message = "initialization vector as hex-encoded text (32 hex-digits):";
		
		return getData(message, scanner);
	}

	
	private static String getData(String message, Scanner scanner) {
		System.out.println("Please provide " + message);
		System.out.print(">");
		
		String data = scanner.nextLine();
		
		return data;
	}
	
	private static String getPassword(Scanner scanner) {
		String message = "password as hex-encoded text (16 bytes, i.e. 32 hex-digits):";
		
		return getData(message, scanner);
	}

	/**
	 * Gets from user digest to check.
	 * @param fileName file for calculating digest
	 * @return String that represents digest for given file
	 */
	private static String getDigestToCheck(String fileName, Scanner scanner) {
		String message = "expected sha-256 digest for " + fileName + ":";
		
		return getData(message, scanner);
	}

	/**
	 * Calculate digest digest for given file
	 * @param filename file for calculating digest
	 * @return byte array that represents digest for given file
	 * @throws Exception throws exception if error occurs
	 */
	public static byte[] createDigest(String filename) throws Exception {
	       InputStream input =  new FileInputStream(filename);

	       byte[] buffer = new byte[4000];
	       MessageDigest complete = MessageDigest.getInstance("SHA-256");
	       int numRead;

	       do {
	           numRead = input.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       input.close();
	       return complete.digest();
	   }

	/**
	 * @param fileName file name
	 * @return digest for given file
	 * @throws Exception throws exception if error occurs
	 */
	private static String getDigest(String fileName) throws Exception {
		byte[] byteArray = 
				createDigest(PATH_TO_FOLDER + fileName);
		
		String result = Util.bytetohex(byteArray);
		
	    return result;
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
