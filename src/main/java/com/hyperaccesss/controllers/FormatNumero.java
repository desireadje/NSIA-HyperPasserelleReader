package com.hyperaccesss.controllers;

public class FormatNumero {

	public static String number_F(String expediteur) {

		String expediteur_formater = null;
		// formatage du numero de l'expéditeur
		int length = expediteur.length();
		String sub = expediteur.substring(0, 4);

		if (length == 12 && sub.equals("+225")) {
			expediteur_formater = expediteur.substring(1, 11);
		} else if (length == 11 && expediteur.substring(0, 4).equals("225")) {
			expediteur_formater = expediteur;
		} else {
			expediteur_formater = expediteur;
		}

		return expediteur_formater;
	}

	private static boolean ValidatePhoneNumber(String phoneNo) {

		// valider les numéros de téléphone au format "1234567890"
		if (phoneNo.matches("\\d{12}"))
			return true;

		// valider le numéro de téléphone avec -,. ou espaces
		else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
			return true;

		// validation du numéro de téléphone avec une longueur d'extension de 3 à 5
		else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
			return true;

		// validating phone number where area code is in braces ()
		else if (phoneNo.matches("\\(\\d{3}\\)"))
			return true;

		// return false if nothing matches the input
		else
			return false;

	}
}
