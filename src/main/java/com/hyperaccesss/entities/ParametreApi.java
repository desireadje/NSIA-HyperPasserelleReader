package com.hyperaccesss.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parametre_api")
public class ParametreApi implements Serializable {

	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_params;

	private String code_compte;
	private String sender;
	private String username;
	private String password;

	private String url_addOneSms;
	private String url_addBulkSms;
	private String url_addFullSms;

	private int etat_params = 1;

	// constructeur sans params
	public ParametreApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParametreApi(String code_compte, String sender, String username, String password, String url_addOneSms,
			String url_addBulkSms, String url_addFullSms, int etat_params) {
		super();
		this.code_compte = code_compte;
		this.sender = sender;
		this.username = username;
		this.password = password;
		this.url_addOneSms = url_addOneSms;
		this.url_addBulkSms = url_addBulkSms;
		this.url_addFullSms = url_addFullSms;
		this.etat_params = etat_params;
	}

	public Long getId_params() {
		return id_params;
	}

	public void setId_params(Long id_params) {
		this.id_params = id_params;
	}

	public String getCode_compte() {
		return code_compte;
	}

	public void setCode_compte(String code_compte) {
		this.code_compte = code_compte;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl_addOneSms() {
		return url_addOneSms;
	}

	public void setUrl_addOneSms(String url_addOneSms) {
		this.url_addOneSms = url_addOneSms;
	}

	public String getUrl_addBulkSms() {
		return url_addBulkSms;
	}

	public void setUrl_addBulkSms(String url_addBulkSms) {
		this.url_addBulkSms = url_addBulkSms;
	}

	public String getUrl_addFullSms() {
		return url_addFullSms;
	}

	public void setUrl_addFullSms(String url_addFullSms) {
		this.url_addFullSms = url_addFullSms;
	}

	public int getEtat_params() {
		return etat_params;
	}

	public void setEtat_params(int etat_params) {
		this.etat_params = etat_params;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
