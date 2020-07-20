package com.hyperaccesss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperaccesss.entities.Passerelle;
import com.hyperaccesss.entities.Sms;

public interface PasserelleRepository extends JpaRepository<Passerelle, Long> {
	
	@Query("SELECT p FROM Passerelle p WHERE p.etat_pass <> -1")
	List<Passerelle> findAllPasserelle();

	// Cette requête retourne une ligne de passerelle
	@Query("SELECT p FROM Passerelle p WHERE p.ip_pass = ?1 AND etat_pass = 1")
	Passerelle findPasserelleByIp(String ip_pass);

}
