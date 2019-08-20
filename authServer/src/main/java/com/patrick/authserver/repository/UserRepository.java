package com.patrick.authserver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.patrick.authserver.auth.beans.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

	Users findByUsername(String username);
	
	Users findByEmail(String email);
}
