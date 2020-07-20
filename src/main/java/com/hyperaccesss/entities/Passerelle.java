package com.hyperaccesss.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "passerelle")
public class Passerelle implements Serializable {

	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pass;

	private String ip_pass;
	private String url_pass;

	private int etat_pass = 1;

	// contructeur sans params
	public Passerelle() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur sans params
	public Passerelle(String ip_pass, String url_pass, int etat_pass) {
		super();
		this.ip_pass = ip_pass;
		this.url_pass = url_pass;
		this.etat_pass = etat_pass;
	}

	public Long getId_pass() {
		return id_pass;
	}

	public void setId_pass(Long id_pass) {
		this.id_pass = id_pass;
	}

	public String getIp_pass() {
		return ip_pass;
	}

	public void setIp_pass(String ip_pass) {
		this.ip_pass = ip_pass;
	}

	public String getUrl_pass() {
		return url_pass;
	}

	public void setUrl_pass(String url_pass) {
		this.url_pass = url_pass;
	}

	public int getEtat_pass() {
		return etat_pass;
	}

	public void setEtat_pass(int etat_pass) {
		this.etat_pass = etat_pass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
