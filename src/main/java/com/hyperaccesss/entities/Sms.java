package com.hyperaccesss.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "sms_in")
public class Sms implements Serializable {
	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSms;
	private String codeSms;
	private String expediteurSms;
	private String message;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateReception;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateInsertion;

	private int etatSms = 0;

	@ManyToOne
	@JoinColumn(name = "ip_pass", referencedColumnName = "ip_pass") /**
																	 * name : nom porté par l'attribut de la classe
																	 * fille(Sms)
																	 * 
																	 * referencedColumnName : attribut de la classe père
																	 * visé (Paserrelle)
																	 * 
																	 * Rg : plusieur sms sont recuperer sur une passerelle
																	 */

	private Passerelle passerelle;

	public Sms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sms(String codeSms, String expediteurSms, String message, Date dateReception, Date dateInsertion,
			int etatSms) {
		super();
		this.codeSms = codeSms;
		this.expediteurSms = expediteurSms;
		this.message = message;
		this.dateReception = dateReception;
		this.dateInsertion = dateInsertion;
		this.etatSms = etatSms;
	}

	public Long getIdSms() {
		return idSms;
	}

	public void setIdSms(Long idSms) {
		this.idSms = idSms;
	}

	public String getCodeSms() {
		return codeSms;
	}

	public void setCodeSms(String codeSms) {
		this.codeSms = codeSms;
	}

	public String getExpediteurSms() {
		return expediteurSms;
	}

	public void setExpediteurSms(String expediteurSms) {
		this.expediteurSms = expediteurSms;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateReception() {
		return dateReception;
	}

	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}

	public Date getDateInsertion() {
		return dateInsertion;
	}

	public void setDateInsertion(Date dateInsertion) {
		this.dateInsertion = dateInsertion;
	}

	public int getEtatSms() {
		return etatSms;
	}

	public void setEtatSms(int etatSms) {
		this.etatSms = etatSms;
	}

	public Passerelle getPasserelle() {
		return passerelle;
	}

	public void setPasserelle(Passerelle passerelle) {
		this.passerelle = passerelle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}