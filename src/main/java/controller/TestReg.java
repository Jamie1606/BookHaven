// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 3.6.2023
// Description: To test string with regular expressions

package controller;

import java.util.regex.*;

public class TestReg {
	private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern passwordPattern = Pattern.compile("^.{8,16}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern phonePattern = Pattern.compile("^[0-9]{8}$");
	private static final Pattern postalCodePattern = Pattern.compile("^[0-9]{6}$");
	public static boolean matchEmail(String test) {
		Matcher m = emailPattern.matcher(test);
		return m.find();
	}

	public static boolean matchPassword(String test) {
		Matcher m = passwordPattern.matcher(test);
		return m.find();
	}
	
	public static boolean matchPhone(String test) {
		Matcher m = phonePattern.matcher(test);
		return m.find();
	}

	public static boolean matchPostalCode(String test) {
		Matcher m = postalCodePattern.matcher(test);
		return m.find();
	}
}