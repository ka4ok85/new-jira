package com.example.entity;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Embeddable
public class ActionID implements java.io.Serializable {

	private static final long serialVersionUID = 4908522195701227506L;

	@Id
	private Long id;

	private User user;

	private Ticket ticket;
 
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user=user;
    }

    @ManyToOne
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket=ticket;
    }

}