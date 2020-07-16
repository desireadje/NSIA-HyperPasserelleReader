package com.hyperaccesss.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hyperaccesss.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	@Query("SELECT c FROM Role c WHERE c.etat <> -1")
	List<Role> findAllRole();
	
	@Query("SELECT c FROM Role c WHERE c.role <> 'Client' AND c.etat <> -1")
	List<Role> findAllRoleAdministration();
	
	@Query("SELECT c FROM Role c WHERE c.role = 'Invite' AND c.etat <> -1")
	List<Role> findAllRoleInvite();
	
	@Query("SELECT c FROM Role c WHERE c.role = 'Client' AND c.etat <> -1")
	List<Role> findAllRoleClient();

	@Query("SELECT c FROM Role c WHERE c.role = ?1 AND c.etat <> -1")
	Role findRoleByName(String role);
}
