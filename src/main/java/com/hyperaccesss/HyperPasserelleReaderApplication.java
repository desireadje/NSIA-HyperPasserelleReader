package com.hyperaccesss;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hyperaccesss.entities.Passerelle;
import com.hyperaccesss.entities.Utilisateur;
import com.hyperaccesss.repositories.PasserelleRepository;
import com.hyperaccesss.repositories.UtilisateurRepository;

@EnableScheduling
@SpringBootApplication
public class HyperPasserelleReaderApplication {

	@Autowired
	private static PasserelleRepository passerelleRepos;

	@Autowired
	UtilisateurRepository utilisateurRepos;

	public static void main(String[] args) {
		SpringApplication.run(HyperPasserelleReaderApplication.class, args);
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

	/*private void create_integration() {

		Utilisateur u = new Utilisateur();

		u.setUsername("admin");
		u.setPassword("$2y$10$njHsJada6JS8e0LSUyQB4OMQC4fzQsDN/r7ugGuqY2GJqN/kAF.5."); // Admin@2018
		u.setCivilite("M.");
		u.setNom("Dev");
		u.setPrenoms("HyperAccesss");
		u.setMobile("22584045665");
		u.setEmail("dev@hyperaccesss.com");
		u.setPhoto("images.jpg");
		u.setEtat(1);
		u.setDateCreation(new Date());
		u.setDateModification(new Date());
		// u.setRole(new Role());

		utilisateurRepos.save(u);

	}
	
	private void create_role() {
		
	}*/

}

/**
 * 1- Lecture des SMS sur chaque passerelle et les enregistrer dans le dossier
 * download
 * 
 * 2- Lecture de chaque fichiers pour les mettre dans la BD, le sms est
 * enregistré en BD avec etat à -1
 * 
 * 3- Lecture des SMS dans la BD des sms du jour et creer un fichier du jour et
 * les mettre dans le dossier IN
 * 
 * Signification des etat_sms 0 : le sms viens d'etre recuperer sur la
 * passerelle et vient d'etre enregistrer dans la BD 1 : le sms viens
 * 
 * 
 */
