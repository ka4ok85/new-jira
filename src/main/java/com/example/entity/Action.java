package com.example.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "actions")
@AssociationOverrides({ @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "id_user") ),
		@AssociationOverride(name = "pk.ticket", joinColumns = @JoinColumn(name = "id_ticket") ) })
public class Action {

	private static final long serialVersionUID = 2180807359929501954L;

	private ActionID pk = new ActionID();

	@Column(name = "action", nullable = false, length = 255)
	private String action;

	@Column(name = "action_date", nullable = false, length = 255)
	private String actionDate;

	@EmbeddedId
	public ActionID getPk() {
		return pk;
	}

	public void setPk(ActionID pk) {
		this.pk = pk;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionDate() {
		return actionDate;
	}

	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

	@Transient
	public User getUser() {
		return getPk().getUser();
	}

	public void setUser(User user) {
		getPk().setUser(user);
	}

	@Transient
	public Ticket getTicket() {
		return getPk().getTicket();
	}

	public void setTicket(Ticket ticket) {
		getPk().setTicket(ticket);
	}

	@Override
	public String toString() {
		return "Action [action=" + action + ", actionDate=" + actionDate + "]";
	}

}
