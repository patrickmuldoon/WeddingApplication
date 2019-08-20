package com.patrick.authserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patrick.authserver.auth.beans.Users;
import com.patrick.authserver.repository.UserRepository;
import com.patrick.authserver.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	
	private BCryptPasswordEncoder passwordEncoder;
	
	private UserRepository userRepository;

	@Autowired
	public void setPasswordEncoder(@Lazy BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Users findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean isUserNameAvailable(String username) {
		Users user = userRepository.findByUsername(username);
		if(user == null)
			return true;
		else
			return false;
	}

	@Override
	public boolean isEmailAvailable(String email) {
		Users user = userRepository.findByEmail(email);
		if(user == null)
			return true;
		else
			return false;
	}

	@Override
	public void saveUser(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Override
	public void deleteUser(long userId) {
		userRepository.deleteById(userId);
		return;
	}
	
}
