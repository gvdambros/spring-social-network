package com.socialnetwork.validation;

public class CommentValidation {
	
	private String commentId;
	private boolean valid;

	public CommentValidation() {}
	
	public CommentValidation(String commentId, boolean valid) {
		super();
		this.commentId = commentId;
		this.valid = valid;
	}
	
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
