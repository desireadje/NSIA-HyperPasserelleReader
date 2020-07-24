package com.hyperaccesss.controllers;

public class FormatNumero {

	public static String number_F(String expediteur) {
		
		String expediteur_formater = null;
		
		// formatage du numero de l'expéditeur
		int length = expediteur.length();
		String sub = expediteur.substring(0, 4);

		if (length == 12 && sub.equals("+225")) {
			expediteur_formater = expediteur.substring(1, 11);
		} else {
			expediteur_formater = expediteur;
		}
		
		return expediteur_formater;
	}
}
