package com.example.entity;

import com.fasterxml.jackson.annotation.JsonView;

public class TicketWrapper extends Ticket {
	
	private static final long serialVersionUID = -7828524924041456561L;

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
