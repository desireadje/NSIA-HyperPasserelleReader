package com.hyperaccesss.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "setting")
public class Setting implements Serializable {

	// Attributs
	@Id
	private String raisonSociale;
	private String sigle;
	private String telephone;
	private String mobile;
	@Email()
	private String email;
	private String logo;
	private String siteWeb;
	private String adresse;
	private String boitePostale;
	private String fax;
	private int etat = 1;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateCreation;	
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;
	
	// Clés étrangères
	@ManyToOne
	@JoinColumn(name = "creerpar", referencedColumnName = "username")
	private Utilisateur creerPar;
	@ManyToOne
	@JoinColumn(name = "modifierpar", referencedColumnName = "username")
	private Utilisateur modifierPar;

	// Constructeurs
	public Setting() {
		super();
	}

	public Setting(String raisonSociale, String sigle, String telephone, String mobile, @Email String email,
			String logo, String siteWeb, String adresse, String boitePostale, String fax, int etat, Date dateCreation,
			Date dateModification) {
		super();
		this.raisonSociale = raisonSociale;
		this.sigle = sigle;
		this.telephone = telephone;
		this.mobile = mobile;
		this.email = email;
		this.logo = logo;
		this.siteWeb = siteWeb;
		this.adresse = adresse;
		this.boitePostale = boitePostale;
		this.fax = fax;
		this.etat = etat;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}

	// Getters and Setters
	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getBoitePostale() {
		return boitePostale;
	}

	public void setBoitePostale(String boitePostale) {
		this.boitePostale = boitePostale;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Utilisateur getCreerPar() {
		return creerPar;
	}

	public void setCreerPar(Utilisateur creerPar) {
		this.creerPar = creerPar;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public Utilisateur getModifierPar() {
		return modifierPar;
	}

	public void setModifierPar(Utilisateur modifierPar) {
		this.modifierPar = modifierPar;
	}

}
