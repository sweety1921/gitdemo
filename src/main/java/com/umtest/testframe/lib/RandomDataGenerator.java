package com.umtest.testframe.lib;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RandomDataGenerator {

	private static Logger logger = LogManager.getLogger(RandomDataGenerator.class);

	public final static String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public final static String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	public final static String NUM = "0123456789";
	public final static String SPL_CHARS = "@$!%^*()";
	static SecureRandom rnd = new SecureRandom();
	
	
	public static String randomChar(String container) {
		String Data = null;
		if (container.contains("random") || container.contains("RANDOM")) {
			if (container.contains("email") || container.contains("EMAIL"))
				Data = generate_email("test.app");
			else if (container.contains("number") || container.contains("NUMBER")) {
				if (container.contains("#")) {
					int size = Integer.parseInt(container.split("#")[1]);	
					int m = (int) Math.pow(10, size - 1);
					Data = Integer.toString(m + new Random().nextInt(9 * m));
					
					Data = generateRandomValue("", size, size, 0, size, 0);
				} else {
					Date dNow = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("MMddhhmmssMs");
					Data = ft.format(dNow);
				}
				logger.info("Data is {} {}", MainUtil.ProjectConst.VALUE.getMsg(), Data);
			} else if (container.contains("char") || container.contains("CHAR")) {
				final Random RANDOM = new SecureRandom();
				String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

				String randomstring = "";
				int size = Integer.parseInt(container.split("#")[1]);
				randomstring = RandomStringUtils.randomAlphabetic(size).toUpperCase();
				/*for (int i = 0; i < size; i++) {
					int index = RANDOM.nextInt() * letters.length();
					randomstring += letters.substring(index, index + 1);				
				}*/
				Data = randomstring;
				logger.info("randomstring {} {}", MainUtil.ProjectConst.VALUE.getMsg(), Data);
			} else if (container.contains("nric") || container.contains("NRIC")) {
				Data = "99" + getCurrentTimeStampforNric() + generate6digitRnadomValue();
			} else
				Data = generateRandomValue("testapp", 4, 6, 1, 1, 1);
		}

		return Data;
	}

	/**
	 * This method is used to get the current time stamp for NRIC
	 * 
	 * @return Date
	 */

	public static String getCurrentTimeStampforNric() {
		logger.info("Get Current Time stamp for NRIC");
		return new SimpleDateFormat("MMdd").format(new Date());
	}

	/**
	 * This method is used to generate 6 digit random value
	 * 
	 * @return 6 digit Integer No
	 */
	public static int generate6digitRnadomValue() {
		logger.info("Generate 6 digit random value");
		int numbers = 100000 + rnd.nextInt() * 899900;
		return numbers;
	}

	/**
	 * This method is used to generate the random email
	 * 
	 * @param prefix Pass the no of character you want as a prefix of email
	 * @return a String 
	 */
	public static String generate_email(String prefix) {
		logger.info("Generate Random email");
		String email = null;
		String name = generateRandomValue("", 3, 20, 1, 1, 0);
		String domain = generateRandomValue("", 3, 5, 1, 1, 0);

		email = prefix + name + "@" + domain + "." + "com";

		return email;
	}

	/**
	 * Method to generate a random value
	 * 
	 * @param prefix Pass the prefix No
	 * @param minLen Pass the Minimum length
	 * @param maxLen Pass the maximum length
	 * @param noOfCAPSAlpha Pass the no of capital Alphabet
	 * @param noOfDigits Pass the total no of digit
	 * @param noOfSplChars Pass the total no of special character
	 * @return a Random String value
	 */
	public static String generateRandomValue(String prefix, int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits,
			int noOfSplChars) {

		if (minLen > maxLen)
			throw new IllegalArgumentException("Min. Length > Max. Length!");
		if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen)
			throw new IllegalArgumentException(
					"Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
		int len = new SecureRandom().nextInt(maxLen - minLen + 1) + minLen;
		char[] pswd = new char[len];
		int index = 0;
		for (int i = 0; i < noOfCAPSAlpha; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
		}
		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
		}
		for (int i = 0; i < noOfSplChars; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
		}
		for (int i = 0; i < len; i++) {
			if (pswd[i] == 0) {
				pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
			}
		}

		String abc = prefix + String.valueOf(pswd);
		return abc;
	}

	/**
	 * To get next index of password
	 * 
	 * @param rnd Pass random no
	 * @param len Pass the length 
	 * @param pswd Pass the password
	 * @return index as an Integer
	 */
	public static int getNextIndex(Random rnd, int len, char[] pswd) {
		int index = rnd.nextInt(len);
		while (pswd[index = rnd.nextInt(len)] != 0)
			;
		return index;
	}

	
	public static void generateCustomerDataUMREX(String registrationType) {
		
		logger.info("Generating Customer Details For UMREX");

		if (registrationType.equals("MALAYSIAN")) {
			MainUtil.dictionary.put("CUSTOMER_ID_TYPE", "MYKAD");
			MainUtil.dictionary.put("CUSTOMER_ID", "900101"+randomChar("randomnumber#6"));
			
		} else {
			MainUtil.dictionary.put("CUSTOMER_ID_TYPE", "PASSPORT");
			MainUtil.dictionary.put("CUSTOMER_ID", "T"+randomChar("randomnumber#7"));
		}
		
		MainUtil.dictionary.put("CUSTOMER_NAME", "TEST AUTO TP"+randomChar("randomchar#5"));
		MainUtil.dictionary.put("CUSTOMER_DOB_DD", "01");
		MainUtil.dictionary.put("CUSTOMER_DOB_MM", "01");
		MainUtil.dictionary.put("CUSTOMER_DOB_YYYY", "1980");
		MainUtil.dictionary.put("CUSTOMER_ADDRESS", "179, Jalan Klang Lama");
		MainUtil.dictionary.put("CUSTOMER_CITY", "KUALA LUMPUR");
		MainUtil.dictionary.put("CUSTOMER_STATE", "KUALA LUMPUR");
		MainUtil.dictionary.put("CUSTOMER_POSTCODE", "58100");
		MainUtil.dictionary.put("CUSTOMER_EMAIL", "Test@U.COM.MY");
		MainUtil.dictionary.put("CUSTOMER_NUMBER", "60182143456");
	}
}
