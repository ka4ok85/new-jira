package com.example.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "comments")
@AssociationOverrides({ @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "id_user") ),
						@AssociationOverride(name = "pk.ticket", joinColumns = @JoinColumn(name = "id_ticket") ) })
public class Comment {
	private CommentID pk = new CommentID();

	@JsonView(com.example.entity.Comment.class)
	@Column(name = "comment", nullable = false, length = 255)
	private String comment;

	@JsonView(com.example.entity.Comment.class)
	@Column(name = "comment_date", nullable = false, length = 255)
	private String commentDate;

	@EmbeddedId
	public CommentID getPk() {
		return pk;
	}

	public void setPk(CommentID pk) {
		this.pk = pk;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
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
		return "Comment [comment=" + comment + ", commentDate=" + commentDate + ", UserId=" + getUser().getId() + ", ticketId=" + getTicket().getId() + ", Id=" + getPk().getId() + "]";
	}

	
}
