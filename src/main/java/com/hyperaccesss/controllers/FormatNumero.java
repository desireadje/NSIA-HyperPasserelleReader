package com.hyperaccesss.controllers;

public class FormatNumero {

	public static String number_F(String expediteur) {

		System.out.println("Est numérique : "+ isNumeric(expediteur));
		// Mes declarations
		String numero = null;
		String indicatif = null;
		int taille = 0;

		// Je recupère la taille du numéro expéditeur
		taille = expediteur.length();

		
		if(isNumeric(expediteur) == true) {
			// Si le numéro existe alors
			if (taille > 0) {		
				
				
				// Je recupère le l'indicatif pays +225
				indicatif = expediteur.substring(0, 4);	
				

				// Si la taille du numéro égale à 12 caractères alors
				if (taille == 12) {

					// Si l'indicatif est +225 alors
					if (indicatif.equalsIgnoreCase("+225")) {
						numero = expediteur.substring(1, 11);
					}
				} else if (taille == 11) {
					// Si l'indicatif est +225 alors
					if (indicatif.equalsIgnoreCase("225")) {
						numero = expediteur;
					}

				} else {
					numero = expediteur;
				}
			}
		}
		

		return numero;
	}

	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
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
