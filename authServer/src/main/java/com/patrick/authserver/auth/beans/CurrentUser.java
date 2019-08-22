package com.patrick.authserver.auth.beans;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class CurrentUser extends User {

	public CurrentUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String email,
			long id, UsersProfile usersProfile) {
		super(username, password, authorities);
		this.email = email;
		this.usersProfile = usersProfile;
		this.id = id;
	}
	
	private String email;
	private long id;
	private UsersProfile usersProfile;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public UsersProfile getUsersProfile() {
		return usersProfile;
	}
	public void setUsersProfile(UsersProfile usersProfile) {
		this.usersProfile = usersProfile;
	}
	
}
