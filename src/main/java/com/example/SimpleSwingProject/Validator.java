package com.example.SimpleSwingProject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	
	public static boolean isValidEmail(String email) {
	    String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	public static boolean isValidPhone(String phone) {
	    // This regex allows for common phone number formats, including
	    // numbers with or without dashes, spaces, or parentheses.
	    String regex = "^(\\d[- .]?){10}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(phone);
	    return matcher.matches();
	}
}
