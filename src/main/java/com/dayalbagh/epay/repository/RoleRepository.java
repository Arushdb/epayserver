package com.dayalbagh.epay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dayalbagh.epay.model.ERole;
import com.dayalbagh.epay.model.Role;




@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(ERole name);

}
