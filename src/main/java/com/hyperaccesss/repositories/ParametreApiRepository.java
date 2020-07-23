package com.hyperaccesss.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperaccesss.entities.ParametreApi;

public interface ParametreApiRepository extends JpaRepository<ParametreApi, Long> {
	
	// Cette requête retourne une ligne de passerelle
	@Query("SELECT p FROM ParametreApi p WHERE etat_params = 1")
	ParametreApi findParamApi();

}
