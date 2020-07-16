package com.hyperaccesss.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyperaccesss.config.GetCurrentUser;
import com.hyperaccesss.entities.Role;
import com.hyperaccesss.entities.Utilisateur;
import com.hyperaccesss.repositories.RoleRepository;
import com.hyperaccesss.repositories.UtilisateurRepository;

@Controller
// @Secured(value = { "ROLE_Admin" })
@RequestMapping(value = "/utilisateur")
public class UtilisateurController {

	// Inversion de control
	@Autowired
	private UtilisateurRepository utilisateurRepos;
	@Autowired
	private RoleRepository roleRepos;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UtilisateurRepository userRepos;

	// Mes variables
	// Je cré une nouvelle instance de mon objet qui recupère l'utilisateur connecté
	GetCurrentUser user = new GetCurrentUser();
	Utilisateur userConnected = null;

	// Logger
	private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);

	/*
	 * Cette fonction retourne la page qui liste
	 */
	@GetMapping()
	public String page(Model model, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);

		// J'active le menu et sous-menu
		model.addAttribute("open_menu", "utilisateur");
		model.addAttribute("active_menu", "utilisateur");

		// System.out.println(utilisateurRepos.findAllUtilisateur().get(0));
		// je recherche les Utilisateurs non supprimées
		model.addAttribute("Utilisateurs", utilisateurRepos.findAllUtilisateurSansClient());
		model.addAttribute("Roles", roleRepos.findAllRole());
		// Je retourne la vue
		return "securites/utilisateur/index";
	}

	/*
	 * Fonction qui affiche par id
	 */
	public Utilisateur findOne(String username) {
		Utilisateur user = null;
		try {
			user = utilisateurRepos.findUtilisateurByName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/*
	 * Cette fonction retourne la page de création
	 */
	@GetMapping(value = "/formcreate")
	public String formcreate(Model model, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);

		// J'active le menu et sous-menu
		model.addAttribute("open_menu", "utilisateur");
		model.addAttribute("active_menu", "utilisateur");
		model.addAttribute("user", new Utilisateur());

		model.addAttribute("Roles", roleRepos.findAllRoleAdministration());

		// Je retourne ma vue
		return "securites/utilisateur/create";
	}

	/*
	 * Fonction de création
	 */
	@PostMapping(value = "/create")
	public String create(@Valid Utilisateur a, BindingResult bindingResult, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);

		try {
			if (bindingResult.hasErrors()) {
				return "securites/utilisateur/create";
			}

			a.setEtat(1);
			a.setDateCreation(new Date());
			a.setDateModification(new Date());
			a.setPassword(passwordEncoder.encode(a.getPassword()));

			a = utilisateurRepos.save(a);

			if (a != null) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/utilisateur";
	}

	/*
	 * Fonction qui affiche la page de modification
	 */
	@GetMapping(value = "/formupdate")
	public String formupdate(String username, Model model, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);
		try {
			// J'active le menu et sous-menu
			model.addAttribute("open_menu", "utilisateur");
			model.addAttribute("active_menu", "utilisateur");
			model.addAttribute("user", findOne(username));
			model.addAttribute("Roles", roleRepos.findAllRoleAdministration());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "securites/utilisateur/update";
	}

	/*
	 * Fonction qui modification
	 */
	@PostMapping(value = "/update")
	public String update(@Valid Utilisateur a, BindingResult bindingResult, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);

		Utilisateur utilisateur = null;
		try {
			if (bindingResult.hasErrors()) {
				return "securites/utilisateur/create";
			}

			utilisateur = findOne(a.getUsername());
			if (utilisateur != null) {

				if (a.getPassword() != null) {
					a.setPassword(passwordEncoder.encode(a.getPassword()));
				} else {
					a.setPassword(utilisateur.getPassword());
				}
				a.setDateModification(new Date());
				utilisateur = utilisateurRepos.save(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/utilisateur";
	}

	// Fonction de suppression d'un cdr
	@GetMapping(value = "/delete")
	public String delete(String username, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);

		Utilisateur utilisateur = null;
		try {
			utilisateur = findOne(username);
			if (utilisateur != null) {
				utilisateur.setEtat(-1);
				utilisateur.setDateModification(new Date());
				utilisateur = utilisateurRepos.save(utilisateur);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/utilisateur";
	}

	@GetMapping(value = "/profil")
	public String profil(Model model, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);

		// J'active le menu et sous-menu
		model.addAttribute("open_menu", "utilisateur");
		model.addAttribute("active_menu", "utilisateur");
		model.addAttribute("utilisateur", new Utilisateur());

		model.addAttribute("User", userConnected);
		model.addAttribute("roles", roleRepos.findAllRole());
		// Je retourne ma vue
		return "securites/utilisateur/profil";
	}

	@GetMapping(value = "/lockscreen")
	public String lockscreen(Model model) {
		return "securites/utilisateur/lockscreen";
	}

	@RequestMapping(value = "/getLogedUser")
	public Map<String, Object> getLogedUser(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String ussername = securityContext.getAuthentication().getName();
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority ga : securityContext.getAuthentication().getAuthorities()) {
			roles.add(ga.getAuthority());
		}

		Map<String, Object> params = new HashMap<>();
		params.put("username", ussername);
		params.put("roles", roles);
		return params;
	}

	@GetMapping(value = "/forgetpassword")
	public String forgetpassword(Model model) {
		return "forget";
	}

	// Fonction de active & desactive compte
	@GetMapping(value = "/active")
	public String active(String username, Model model, HttpSession session) {

		// Je recupère le userName de l'utilisateur connecté
		userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
		session.setAttribute("user", userConnected);
		Utilisateur t = null;

		try {
			t = findOne(username);
			if (t != null) {
				if (t.getEtat() == 0) {
					t.setEtat(1);
				} else {
					t.setEtat(0);
				}
				t.setModifierPar(userConnected);
				t.setDateModification(new Date());

				t = userRepos.save(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/utilisateur";
	}

}
