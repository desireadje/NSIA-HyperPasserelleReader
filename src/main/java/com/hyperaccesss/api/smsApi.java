package com.hyperaccesss.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hyperaccesss.entities.ParametreApi;
import com.hyperaccesss.repositories.ParametreApiRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/smsapi")
public class smsApi {

	@Autowired
	private ParametreApiRepository parametreApiRepos;

	private static final String uri = "https://smspro.hyperaccesss.com/api/addFullSms";

	private static final String add_one_sms_uri = "https://smspro.hyperaccesss.com:8443/api/addOneSms?Code=CMPTO20200320203638&Sender=HAS&Sms=Test&Dest=22584046064&Username=has&Password=123456";

	private static final RequestMethod[] POST = null;

	// Call addOneSms
	// http: //localhost:8430/smsapi/addOneSms?Sms=Test&Dest=22584046064
	@RequestMapping(value = "/addOneSms", method = RequestMethod.GET)
	public ResponseEntity<String> addOneSms(@RequestParam("Code") String Code, @RequestParam("Sender") String Sender,
			@RequestParam("Sms") String Sms, @RequestParam("Dest") String Dest,
			@RequestParam("Username") String Username, @RequestParam("Password") String Password) {

		ParametreApi api = null;
		ResponseEntity<String> response = null;

		// je recupere le parametre api
		api = parametreApiRepos.findParamApi();

		if (api != null) {
			String uri_add_one_sms = "https://smspro.hyperaccesss.com:8443/api/addOneSms?Code=" + api.getCode_compte() + "&Sender="
					+ api.getSender() + "&Sms=" + Sms + "&Dest=" + Dest + "&Username=" + api.getUsername() + "&Password=" + api.getPassword() + "";

			RestTemplate restTemplate = new RestTemplate();

			response = restTemplate.getForEntity(uri_add_one_sms, String.class);
		}
		
		return response;
	}
	
	/*@PostMapping(value = "/addFullSms")
	public Object addFullSms(@RequestBody Object test) throws Exception {
		ParametreApi api = null;
		// je recupere le parametre api
		api = parametreApiRepos.findParamApi();

		String uri_add_full_sms = api.getUrl_addFullSms();

		RestTemplate restTemplate = new RestTemplate();

		// Je cré une instance de HttpHeaders()
		HttpHeaders headers = new HttpHeaders();

		// Je definis mon header pour retouner du JSON
		headers.setContentType(MediaType.APPLICATION_JSON);

		// HttpEntity<String> entity = new HttpEntity<String>(object, headers);
		HttpEntity<String> entity = new HttpEntity<String>(test.toString(), headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri_add_full_sms, entity, String.class);

		return response;
	}*/
	
	

}
