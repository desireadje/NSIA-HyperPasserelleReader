package com.hyperaccesss.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperaccesss.entities.Sms;

public interface SmsRepository extends JpaRepository<Sms, Long> {

	// Cette requête retourne une ligne de sms (un object de type sms)
	@Query("SELECT s FROM Sms s WHERE s.codeSms = ?1")
	Sms findSmsInByCode(String codesms);

	@Query("SELECT s FROM Sms s WHERE s.passerelle.ip_pass= ?1 AND DATE(s.dateInsertion) = CURRENT_DATE AND s.etatSms = -1")
	List<Sms> findAllSmsByIpPasserelle(String ip);

}
