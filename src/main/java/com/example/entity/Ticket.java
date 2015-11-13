package com.example.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "tickets")
public class Ticket extends AbstractPersistable<Long>{

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.ticket", cascade=CascadeType.ALL)
	Set<Action> UserTickets = new HashSet<Action>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.ticket", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	Set<Comment> UserComments = new HashSet<Comment>();

	private static final long serialVersionUID = 8640946894564355389L;

	@JsonView(com.example.entity.Ticket.class)
	private String title;
	@JsonView(com.example.entity.Ticket.class)
	private String description;
	@JsonView(com.example.entity.Ticket.class)
	private String priority;
	@JsonView(com.example.entity.Ticket.class)
	private String status;
	@JsonView(com.example.entity.Ticket.class)
	private String ticketAdded;
	@JsonView(com.example.entity.Ticket.class)
	private String ticketUpdated;
	

	public Ticket() {
	}

	@Column(name = "title", unique = false, nullable = false, length = 255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", unique = false, nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "priority", unique = false, nullable = false, length = 255)
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Column(name = "status", unique = false, nullable = false, length = 255)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ticket_added", unique = false, nullable = false, length = 255)
	public String getTicketAdded() {
		return ticketAdded;
	}

	public void setTicketAdded(String ticketAdded) {
		this.ticketAdded = ticketAdded;
	}

	@Column(name = "ticket_updated", unique = false, nullable = false, length = 255)
	public String getTicketUpdated() {
		return ticketUpdated;
	}

	public void setTicketUpdated(String ticketUpdated) {
		this.ticketUpdated = ticketUpdated;
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
		return "Ticket [title=" + title + ", description=" + description + ", priority=" + priority + ", status="
				+ status + ", ticketAdded=" + ticketAdded + ", ticketUpdated=" + ticketUpdated + "]";
	}


}
	
	