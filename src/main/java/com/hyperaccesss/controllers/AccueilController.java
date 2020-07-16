package com.hyperaccesss.controllers;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyperaccesss.config.GetCurrentUser;
import com.hyperaccesss.entities.Utilisateur;
import com.hyperaccesss.repositories.UtilisateurRepository;


@Controller
@RequestMapping(value = "/")
public class AccueilController {

	// Inversion de contrôle
	@Autowired
	private UtilisateurRepository userRepos;

	// Logger
	private static final Logger log = LoggerFactory.getLogger(AccessDeniedController.class);

	// Je cré une nouvelle instance de mon objet qui recupère l'utilisateur connecté
	GetCurrentUser user = new GetCurrentUser();
	Integer nbreClient = 0;
	Integer nbreCompte = 0;
	Integer nbreSender = 0;
	Integer nbreUser = 0;
	Double solde = 0.0;

	Utilisateur userConnected = null;
	Integer smsEnvoyes = 0;
	Integer smsRecus = 0;
	Integer smsNonRecu = 0;
	Integer smsEncours = 0;

	// Cette fonction affiche les information du tableau de bord
	@GetMapping()
	public String page(Model model, HttpSession session) {

		String page = "";
		

		try {
			// Je recupère le userName de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);

			// Si l'utilisateur connecté a le rôle Admin
			if (userConnected.getRole().getRole().equalsIgnoreCase("superadmin")) {
				

				model.addAttribute("listSms", null);
				model.addAttribute("nbreCompte", nbreCompte);
				model.addAttribute("nbreClient", nbreClient);
				model.addAttribute("nbreSender", nbreSender);
				model.addAttribute("nbreUser", nbreUser);

				// je mets mes données dans mes modèles
				model.addAttribute("smsEnvoyes", smsEnvoyes != null ? smsEnvoyes : 0);
				model.addAttribute("smsRecus", smsRecus != null ? smsRecus : 0);
				model.addAttribute("smsNonRecu", (smsNonRecu != null) ? smsNonRecu : 0);
				model.addAttribute("smsEncours", smsEncours != null ? smsEncours : 0);
				

				page = "index";

			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "accueil/" + page;
	}

}
