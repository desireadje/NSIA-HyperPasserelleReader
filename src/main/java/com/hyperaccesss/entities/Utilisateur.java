package com.hyperaccesss.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

	// Attributs
	@Id
	private String username;
	private String password;
	private String civilite;
	@NotEmpty()
	@Size(min = 2, max = 30)
	private String nom;
	private String prenoms;
	@NotEmpty()
	@Size(min = 8, max = 20)
	private String mobile;
	private String telephone;
	@NotEmpty()
	@Email
	private String email;
	private String photo;
	private int etat = 1;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateCreation;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;
	
	// Clés étrangères
	@ManyToOne
	@JoinColumn(name = "modifierpar", referencedColumnName = "username")
	private Utilisateur modifierPar;
	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "role")
	private Role role;
	@ManyToOne
	@JoinColumn(name = "creerpar", referencedColumnName = "username")
	private Utilisateur creerPar;

	// Constructeurs
	public Utilisateur() {
		super();
	}

	public Utilisateur(String username, String password, String civilite, String nom, String prenoms, String mobile,
			String telephone, String email, String photo, int etat, Date dateCreation, Date dateModification) {
		super();
		this.username = username;
		this.password = password;
		this.civilite = civilite;
		this.nom = nom;
		this.prenoms = prenoms;
		this.mobile = mobile;
		this.telephone = telephone;
		this.email = email;
		this.photo = photo;
		this.etat = etat;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}

	
	// Getters and Setters
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

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
