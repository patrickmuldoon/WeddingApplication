package com.patrick.authserver.service;

import org.springframework.stereotype.Service;

import com.patrick.authserver.auth.beans.Users;

@Service
public interface UsersService {

	Users findByUsername(String username);
	
	boolean isUserNameAvailable(String username);
	
	boolean isEmailAvailable(String email);
	
	void saveUser(Users user);
	
	void deleteUser(long userId);
}
