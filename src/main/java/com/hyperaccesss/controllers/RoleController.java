package com.hyperaccesss.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyperaccesss.entities.Role;
import com.hyperaccesss.repositories.RoleRepository;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

	// Inversion de control
	@Autowired
	private RoleRepository roleRepos;

	// Logger
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);

	// url de page appel
	@GetMapping()
	public String page(Model model) {
		model.addAttribute("active_menu", "role");
		model.addAttribute("open_menu", "role");

		model.addAttribute("roles", roleRepos.findAllRole());
		return "securites/role/index";
	}

	// Cette fonction retourne la page de création d'une société
	@GetMapping(value = "/formcreate")
	public String formcreate(Model model) {
		model.addAttribute("active_menu", "role");
		model.addAttribute("open_menu", "role");

		model.addAttribute("role", new Role());
		// Je retourne ma vue
		return "securites/role/create";
	}

	// Fonction de création d'un cdr
	@PostMapping(value = "/create")
	public String create(@Valid Role a, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return "securites/role/create";
			}

			a.setEtat(1);
			a.setDateCreation(new Date());
			a.setDateModification(new Date());

			a = roleRepos.save(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/role";
	}

	// Fonction de recherche d'un
	public Role findOne(String role) {
		Role r = null;
		try {
			r = roleRepos.findRoleByName(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	// Fonction qui retourne la page de modification
	@GetMapping(value = "/formupdate")
	public String formupdate(String roleName, Model model) {
		try {
			model.addAttribute("active_menu", "role");
			model.addAttribute("open_menu", "role");

			Role role = findOne(roleName);
			model.addAttribute("role", role);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "securites/role/update";
	}

	// Cette fonction permet de modifier un pays
	@PostMapping(value = "/update")
	public String update(@Valid Role a, BindingResult bindingResult) {
		Role role = null;
		try {
			if (bindingResult.hasErrors()) {
				return "securites/role/update";
			}
			role = findOne(a.getRole());
			if (role != null) {

				a.setDateModification(new Date());
				role = roleRepos.save(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/role";
	}

	// Fonction de recherche de tous les cdr
	@PostMapping(value = "/findAll")
	public List<Role> findAll() {
		List<Role> roles = null;
		try {
			roles = roleRepos.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

	@PostMapping(value = "/findAllRole")
	public List<Role> findAllRole() {
		List<Role> Roles = null;
		try {
			Roles = roleRepos.findAllRole();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Roles;
	}

	// Fonction de suppression d'un cdr
	@GetMapping(value = "/delete")
	public String delete(String roleName) {
		Role role = null;
		try {
			role = findOne(roleName);
			if (role != null) {
				role.setEtat(-1);
				role.setDateModification(new Date());
				role = roleRepos.save(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/role";
	}

	// Fonction d'activation d'un cdr
	@PostMapping(value = "/active")
	public Role active(@RequestBody String roleName) {
		Role role = null;
		try {
			role = findOne(roleName);
			if (role != null) {

				if (role.getEtat() == 1) {
					role.setEtat(0);
				} else {
					role.setEtat(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}
}
