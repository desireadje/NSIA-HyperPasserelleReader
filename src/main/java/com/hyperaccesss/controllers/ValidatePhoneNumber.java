package com.hyperaccesss.controllers;

public class ValidatePhoneNumber {
	
	@SuppressWarnings("unused")
	private boolean valide(String phoneNo) {
		
		//validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}")) return true;
		
		//validating phone number with -, . or spaces
		else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
		
		//validating phone number with extension length from 3 to 5
		else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
		
		//validating phone number where area code is in braces ()
		else if(phoneNo.matches("\\(\\d{3}\\)")) return true;
		
		//return false if nothing matches the input
		else return false;
		
	}

}
