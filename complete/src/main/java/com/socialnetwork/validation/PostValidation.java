package com.socialnetwork.validation;

public class PostValidation {

	private String postId;
	private boolean valid;
	
	public PostValidation() {}
	
	public PostValidation(String postId, boolean valid) {
		this.postId = postId;
		this.valid = valid;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}	
	
}
