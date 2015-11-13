package com.example.entity;

import com.fasterxml.jackson.annotation.JsonView;

public class TicketWrapper extends Ticket {
	/*
	@JsonView(com.example.entity.TicketWrapper.class)
	private Ticket ticket;
	@JsonView(com.example.entity.TicketWrapper.class)
	private User user;

	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	*/
	
	@JsonView(com.example.entity.TicketWrapper.class)
	private Long userId;
	@JsonView(com.example.entity.TicketWrapper.class)
	private String login;

	public TicketWrapper(Ticket ticket) {
		this.setDescription(ticket.getDescription());
		this.setPriority(ticket.getPriority());
		this.setStatus(ticket.getStatus());
		this.setTitle(ticket.getTitle());
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	
}
