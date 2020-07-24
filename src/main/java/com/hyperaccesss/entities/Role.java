package com.hyperaccesss.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "role")
public class Role implements Serializable {

	// Attributs
	@Id
	private String role;
	private String description;
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
	public Role() {
		super();
	}

	public Role(String role, String description, int etat, Date dateCreation, Date dateModification) {
		super();
		this.role = role;
		this.description = description;
		this.etat = etat;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}

	// Getters and Setters
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public Utilisateur getCreerPar() {
		return creerPar;
	}

	public void setCreerPar(Utilisateur creerPar) {
		this.creerPar = creerPar;
	}

	public Utilisateur getModifierPar() {
		return modifierPar;
	}

	public void setModifierPar(Utilisateur modifierPar) {
		this.modifierPar = modifierPar;
	}

}
