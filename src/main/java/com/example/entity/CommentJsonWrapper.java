package com.example.entity;

public class CommentJsonWrapper {
	private String comment;
	private Long userId;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "CommentJsonWrapper [comment=" + comment + ", userId=" + userId + "]";
	}
	
	
	

}
