package com.hyperaccesss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hyperaccesss.entities.Passerelle;
import com.hyperaccesss.repositories.PasserelleRepository;

@EnableScheduling
@SpringBootApplication
public class HyperPasserelleReaderApplication {

	@Autowired
	private static PasserelleRepository passerelleRepos;

	public static void main(String[] args) {
		SpringApplication.run(HyperPasserelleReaderApplication.class, args);

		// HyperPasserelleReaderApplication app = new HyperPasserelleReaderApplication();
		// app.create_passerelle();

	}

	// Cette fonction permet d'encoder les mots de passe en BCrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// cette fonction permet de creer une passerelle par defaut
	private void create_passerelle() {

		Passerelle passerelle = passerelleRepos.findPasserelleByIp("192.168.9.2");

		if (passerelle != null) {
			System.out.println("Jai trouver quelque chose");
		} else {
			String URL = "http://[IP]/cb/LCR-CDRs.php?password=admin&action=get&filename=[FILE_NAME]&filetype=cdr&internal=0&format=text";
			passerelleRepos.save(new Passerelle("192.168.9.2", URL, 1));
		}
	}

}


/** 
 * 1- Lecture des SMS sur chaque passerelle et les enregistrer dans le dossier download
 * 
 * 2- Lecture de chaque fichiers pour les mettre dans la BD, le sms est enregistré en BD avec etat à -1
 * 
 * 3- Lecture des SMS dans la BD des sms du jour et creer un fichier du jour et les mettre dans le dossier IN
 * 
 * Signification des etat_sms
 * 0 : le sms viens d'etre recuperer sur la passerelle et vient d'etre enregistrer dans la BD
 * 1 : le sms viens 
 * 
 * 
 * */
