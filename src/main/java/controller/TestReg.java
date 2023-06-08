// Author: Zay Yar Tun
// Admin No: 2235035
// Date: 7.6.2023
// Description: To test string with regular expressions

package controller;

import java.util.ArrayList;
import java.util.regex.*;

public class TestReg {
	private static final String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	private static final String passwordPattern = "^.{8,16}$";
	private static final String isbnPattern = "^[\\d]{3}-[\\d]{10}$";
	private static final String integerPattern = "^[\\d]+$";
	private static final String decimalPattern = "^[\\d]+[.]{0,1}[\\d]*$";
	private static final String datePattern = "^[\\d]{4}-[\\d]{2}-[\\d]{2}$";
	private static final String phonePattern = "^[0-9]{8}$";
	private static final String postalCodePattern = "^[0-9]{6}$";

	public static boolean matchEmail(String test) {
		return Pattern.matches(emailPattern, test);
	}

	public static boolean matchPassword(String test) {
		return Pattern.matches(passwordPattern, test);
	}
	public static boolean matchISBN(String test) {
		return Pattern.matches(isbnPattern, test);
	}
	
	public static boolean matchInteger(String test) {
		return Pattern.matches(integerPattern, test);
	}
	
	public static boolean matchDecimal(String test) {
		return Pattern.matches(decimalPattern, test);
	}
	
	public static boolean matchDate(String test) {
		return Pattern.matches(datePattern, test);
	}
	
	public static boolean matchIntegerArrayList(ArrayList<String> test) {
		for(int i = 0; i < test.size(); i++) {
			if(!Pattern.matches(integerPattern, test.get(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean matchPhone(String test) {
		return Pattern.matches(phonePattern, test);
	}

	public static boolean matchPostalCode(String test) {
		return Pattern.matches(postalCodePattern, test);
	}
}