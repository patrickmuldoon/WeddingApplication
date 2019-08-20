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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UsersProfile {
	
	@Id
    @Column(nullable=false, name="USER_PROFILE_ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERID_SEQUENCE")
	@SequenceGenerator(name="USER_PROFILE_ID_SEQUENCE", sequenceName="USER_PROFILE_ID_SEQUENCE")
	private long id;

	private String firstName;
	
	private String lastName;
	
	private String bio;
	
	@JsonIgnore
    @OneToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private Users users;

	public UsersProfile() {
		super();
	}
	
	public UsersProfile(long id, String firstName, String lastName, String bio, Users users) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.users = users;
	}

	public UsersProfile(String firstName, String lastName, Users user) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.users = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
}
