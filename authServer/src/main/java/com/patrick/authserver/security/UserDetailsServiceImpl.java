package com.patrick.authserver.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.patrick.authserver.auth.beans.CurrentUser;
import com.patrick.authserver.auth.beans.Users;
import com.patrick.authserver.repository.UserRepository;
import com.patrick.authserver.service.UsersService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username);
		if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            	.commaSeparatedStringToAuthorityList("ROLE_" + user.getRole());
		return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user.getEmail(), user.getId(), user.getUsersProfile());

	}

	
}
