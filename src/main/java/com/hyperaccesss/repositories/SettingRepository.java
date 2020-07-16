package com.hyperaccesss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperaccesss.entities.Setting;

public interface SettingRepository extends JpaRepository<Setting, String> {

	// Cette requete retourne la société
	@Query("SELECT s FROM Setting s WHERE s.etat <> -1")
	List<Setting> findAllSociete();
	
	// Cette requete retourne la société par sa raisonSociale
	@Query("SELECT s FROM Setting s WHERE s.raisonSociale = ?1 AND s.etat <> -1")
	Setting findAllSocieteByRaisonSociale(String raisonSociale);
}
