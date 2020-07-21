package com.hyperaccesss.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "https://smspro.hyperaccesss.com")
@RequestMapping(value = "/smsapi")
public class smsApi {

	private static final String uri = "https://smspro.hyperaccesss.com/api/addFullSms";

	// Call addOneSms
	@RequestMapping(value = "/addOneSms", method = RequestMethod.GET)
	public ResponseEntity addOneSms(@RequestParam("Code") String Code, @RequestParam("Sender") String Sender,
			@RequestParam("Sms") String Sms, @RequestParam("Dest") String Dest,
			@RequestParam("Username") String Username, @RequestParam("Password") String Password) {

		String uri_add_one_sms = "https://smspro.hyperaccesss.com:8443/api/addOneSms?Code=" + Code + "&Sender=" + Sender
				+ "&Sms=" + Sms + "&Dest=" + Dest + "&Username=" + Username + "&Password=" + Password + "";

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity(uri_add_one_sms, String.class);

		return response;
	}

}
