package com.hyperaccesss.controllers;

public class FormatNumero {

	public static String number_F(String expediteur, String part) {
		
		// formatage du numero de l'expéditeur
		int length = expediteur.length();
	    String sub = expediteur.substring(0, 4);
		
		String exp = null;
		if (length == 12 && sub == "+225") {
			exp = part.substring(1, 11);										
		}
		
		return exp;
	}

}
