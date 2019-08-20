package com.patrick.authserver.auth.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Users {

	@Column(name="USERNAME", nullable=false, unique=true)
	private String username;
	
	@Column(name="EMAIL", nullable=false, unique=true)
	private String email;
	
	@Id
    @Column(nullable=false, name="USERID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERID_SEQUENCE")
	@SequenceGenerator(name="USERID_SEQUENCE", sequenceName="USERID_SEQUENCE")
	private Long id;
	
	@Column(name="PASSWORD", nullable=false)
    private String password;
    
    private String role;
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private UsersProfile usersProfile;
    
	public Users() {
		super();
	}
	
	public Users(RegistrationRequest request) {
		super();
		this.username = request.getUsername();
		this.email = request.getEmail();
		this.password = request.getPassword();
		this.usersProfile = new UsersProfile(request.getFirstName(), request.getLastName(), this);
	}
	
	public Users(String username, String email, Long id, String password, String role, UsersProfile usersProfile) {
		super();
		this.username = username;
		this.email = email;
		this.id = id;
		this.password = password;
		this.role = role;
		this.usersProfile = usersProfile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UsersProfile getUsersProfile() {
		return usersProfile;
	}

	public void setUsersProfile(UsersProfile usersProfile) {
		this.usersProfile = usersProfile;
	}
	
}
