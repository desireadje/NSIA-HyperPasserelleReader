package com.hyperaccesss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperaccesss.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

	@Query("SELECT c FROM Utilisateur c WHERE c.etat <> -1")
	List<Utilisateur> findAllUtilisateur();
	
	@Query("SELECT c FROM Utilisateur c WHERE c.role.role <> 'Client' AND c.etat <> -1")
	List<Utilisateur> findAllUtilisateurSansClient();

	@Query("SELECT c FROM Utilisateur c WHERE c.username = ?1 AND c.etat <> -1")
	Utilisateur findUtilisateurByName(String username);

	@Query("SELECT c FROM Utilisateur c WHERE c.username = ?1 AND c.password = ?2 AND c.etat <> -1")
	Utilisateur login(String username, String password);
	
	@Query("SELECT c FROM Utilisateur c WHERE c.role.role = 'Client' AND c.etat <> -1")
	List<Utilisateur> findAllClient();
	
	@Query("SELECT c FROM Utilisateur c WHERE c.creerPar.username = ?1 AND c.role.role = 'Client' AND c.etat <> -1")
	List<Utilisateur> findAllClientCreatedByCommercial(String username);
	
	@Query("SELECT COUNT(c.nom) FROM Utilisateur c  WHERE c.role.role = 'Client' AND c.etat <> -1")
	Integer countClient();
	
	@Query("SELECT COUNT(c.nom) FROM Utilisateur c  WHERE c.etat <> -1")
	Integer countUser();
	
	// Cette requête retourne un client par commercial
	@Query("SELECT c FROM Utilisateur c WHERE c.creerPar.username = ?1 AND c.role.role = 'Client' AND c.etat <> -1")
	List<Utilisateur> findAllClientByCommercial(String username);
	
	// Cette requête retourne un client par commercial
	@Query("SELECT c FROM Utilisateur c WHERE c.creerPar.username = ?1 AND c.role.role = 'Invite' AND c.etat <> -1")
	List<Utilisateur> findAllClientUsers(String username);

}
