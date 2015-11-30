package com.example.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "users")
public class User extends AbstractPersistable<Long>{

	private static final long serialVersionUID = 8640946896564355389L;

	@JsonView(com.example.entity.User.class)
	@Column(name = "login", unique = true, nullable = false, length = 255)
	private String login;

	@Column(name = "password", unique = false, nullable = false, length = 255)
	private String password;
	
	@JsonView(com.example.entity.User.class)
	@Column(name = "first_name", unique = false, nullable = false, length = 255)
	private String firstName;
	
	@JsonView(com.example.entity.User.class)
	@Column(name = "last_name", unique = false, nullable = false, length = 255)
	private String lastName;

	@JsonView(com.example.entity.User.class)
	@Column(name = "status", unique = false, nullable = false, length = 255)	
	private String status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	Set<Action> UserTickets = new HashSet<Action>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	Set<Comment> UserComments = new HashSet<Comment>();
	
	public User() {
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Action> getUserTickets() {
		return UserTickets;
	}

	public void setUserTickets(Set<Action> userTickets) {
		UserTickets = userTickets;
	}

	public Set<Comment> getUserComments() {
		return UserComments;
	}

	public void setUserComments(Set<Comment> userComments) {
		UserComments = userComments;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", status=" + status + "]";
	}
	
}
