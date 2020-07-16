package com.hyperaccesss.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/403")
public class AccessDeniedController {

	// Logger
	private static final Logger log = LoggerFactory.getLogger(AccessDeniedController.class);

	// Cette fonction retourne la page 403
	@GetMapping()
	public String page(Model model) {
		// Je retourne la vue
		return "403";
	}

}
