package com.hyperaccesss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperaccesss.entities.Sms;

public interface SmsRepository extends JpaRepository<Sms, Long> {

	// Cette requête retourne une ligne de sms
	@Query("SELECT s FROM Sms s WHERE s.codeSms = ?1")
	Sms findSmsInByCode(String codesms);

}
