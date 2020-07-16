package com.hyperaccesss.controllers;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hyperaccesss.config.GetCurrentUser;
import com.hyperaccesss.entities.Setting;
import com.hyperaccesss.entities.Utilisateur;
import com.hyperaccesss.repositories.SettingRepository;
import com.hyperaccesss.repositories.UtilisateurRepository;

@Controller
@RequestMapping(value = "/setting")
public class SettingController {

	// Inversion de contrôle
	@Autowired
	private SettingRepository settingRepos;
	@Value("${dir.images}")
	private String imageDir;

	@Autowired
	private UtilisateurRepository userRepos;
	
	// Mes variables
	// Je cré une nouvelle instance de mon objet qui recupère l'utilisateur connecté
	GetCurrentUser user = new GetCurrentUser();
	Utilisateur userConnected = null;

	// Logger
	private static final Logger log = LoggerFactory.getLogger(SettingController.class);

	// Cette fonction retourne la page qui liste les sociétés
	@GetMapping()
	public String index(Model model, HttpSession session) {

		// J'active le menu
		model.addAttribute("open_menu", "setting");
		model.addAttribute("active_menu", "setting");

		try {
			// Je recupère le username de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);

			// Je recupère la liste des setting
			List<Setting> settings = findAllSociete();

			// Je mets ma liste dans mon model pour l'afficher dans ma vue
			model.addAttribute("Settings", settings);
		} catch (Exception e) {
			e.getMessage();
		}

		// Je retourne ma vue
		return "parametres/setting/index";
	}

	// Cette fonction retourne la page de création d'une société
	@GetMapping(value = "/formcreate")
	public String formSociete(Model model, HttpSession session) {

		// J'active le menu
		model.addAttribute("open_menu", "setting");
		model.addAttribute("active_menu", "setting");
		
		try {
			// Je recupère le userName de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);			
			
			// Je mets ma liste dans mon model pour l'afficher dans ma vue
			model.addAttribute("Setting", new Setting());
		} catch (Exception e) {
			e.getMessage();
		}
		
		// Je retourne ma vue
		return "parametres/setting/create";
	}

	// Fonction de création de création d'une société
	@PostMapping(value = "/create")
	public String create(@Valid Setting setting, BindingResult bindingResult,
			@RequestParam(name = "picture") MultipartFile file, HttpSession session) {
		
		try {
			
			// Je recupère le userName de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);
			
			// Je valide le formulaire
			if (bindingResult.hasErrors()) {
				return "parametres/setting/create";
			}

			if (!(file.isEmpty())) {
				setting.setLogo(file.getOriginalFilename());
				// file.transferTo(new
				// File(System.getProperty("user.home")+"/hyperbilling/"+file.getOriginalFilename()));
				file.transferTo(new File(imageDir + file.getOriginalFilename()));
			}

			// Je charge mes données
			setting.setDateCreation(new Date());
			setting.setCreerPar(userConnected);
			setting.setEtat(1);
			
			// J'enregistre
			settingRepos.save(setting);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Je retourne ma vue
		return "redirect:/setting";
	}

	// Cette fonction d'un cdr
	@PostMapping(value = "/update")
	public String update(@Valid Setting setting, BindingResult bindingResult,
			@RequestParam(name = "picture") MultipartFile file, Model model, HttpSession session) {

		// Mes variables
		Setting retour = null;
		
		try {
			
			// Je recupère le userName de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);
			
			// Je valide mon formulaire
			if (bindingResult.hasErrors()) {
				return "parametres/setting/update";
			}

			// Je recherche un setting par la raison sociale
			retour = settingRepos.findAllSocieteByRaisonSociale(setting.getRaisonSociale());

			if (!(file.isEmpty())) {
				setting.setLogo(file.getOriginalFilename());
				// file.transferTo(new
				// File(System.getProperty("user.home")+"/hyperbilling/"+file.getOriginalFilename()));
				file.transferTo(new File(imageDir + file.getOriginalFilename()));
			} else {
				setting.setLogo(retour.getLogo());
			}

			if (retour != null) {
				setting.setDateModification(new Date());
				setting.setModifierPar(userConnected);
				settingRepos.save(setting);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Je retourne ma vue
		return "redirect:/setting";
	}

	// Fonction de suppression
	@GetMapping(value = "/formupdate")
	public String formupdate(String raisonSociale, Model model, HttpSession session) {

		// J'active le menu
		model.addAttribute("open_menu", "setting");
		model.addAttribute("active_menu", "setting");
		
		try {

			// Je recupère le userName de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);

			// Je recherche un setting par sa raison sociale
			Setting setting = findOne(raisonSociale);
			
			// Je mets ma liste dans mon model pour l'afficher dans ma vue
			model.addAttribute("Setting", setting);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Je retourne ma vue
		return "parametres/setting/update";
	}

	// Fonction de recherche d'un cdr par son id
	@PostMapping(value = "/findOne")
	public Setting findOne(@RequestBody String raisonSociale) {
		Setting setting = null;
		try {
			setting = settingRepos.findAllSocieteByRaisonSociale(raisonSociale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setting;
	}

	// Fonction de recherche de tous les cdr
	@PostMapping(value = "/findAllSociete")
	public List<Setting> findAllSociete() {
		List<Setting> settings = null;
		try {
			settings = settingRepos.findAllSociete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return settings;
	}

	// Fonction de recherche de tous les cdr
	@PostMapping(value = "/findAll")
	public List<Setting> findAll() {
		List<Setting> settings = null;
		try {
			settings = settingRepos.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return settings;
	}

	// Fonction de suppression d'un cdr
	@GetMapping(value = "/delete")
	public String delete(String raisonSociale, HttpSession session) {

		// Mes variables
		Setting setting = null;
		try {
			
			// Je recupère le userName de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);
			
			// Je recherche mon setting par sa raison sociale
			setting = findOne(raisonSociale);
			if (setting != null) {
				setting.setEtat(-1);
				setting.setDateModification(new Date());
				setting.setModifierPar(userConnected);
				
				// Je faire la mise à jour
				setting = settingRepos.save(setting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Je retourne ma vue
		return "redirect:/setting";
	}

	// Fonction d'activation d'un cdr
	@PostMapping(value = "/active")
	public Setting active(@RequestBody String raisonSociale, HttpSession session) {

		//Mes variables
		Setting setting = null;
		try {
			
			// Je recupère le username de l'utilisateur connecté
			userConnected = userRepos.findUtilisateurByName(user.getUserConnected());
			session.setAttribute("user", userConnected);
			
			// Je recherche mon setting par sa raison sociale
			setting = findOne(raisonSociale);
			if (setting != null) {

				if (setting.getEtat() == 1) {
					setting.setEtat(0);
				} else {
					setting.setEtat(1);
				}
				
				setting.setDateModification(new Date());
				setting.setModifierPar(userConnected);
				
				// Je faire la mise à jour
				setting = settingRepos.save(setting);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setting;
	}

}
