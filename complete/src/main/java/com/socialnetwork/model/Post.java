package com.socialnetwork.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Post {

	@Id
	private String id;
	private String author;
	private String title;
	private String content;

	@CreatedDate
	private Date createdDate;
	
	private Date validatedDate; 

	@DBRef(lazy = true)
	@JsonIgnore
	private List<Comment> comments;

	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getValidatedDate() {
		return validatedDate;
	}

	public void setValidatedDate(Date validatedDate) {
		this.validatedDate = validatedDate;
	}
	
	@JsonIgnore
	public boolean isValidated() {
		return this.validatedDate != null;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment newComment) {
		if (this.comments == null) {
			this.comments = new ArrayList<Comment>();
		}
		this.comments.add(newComment);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Post))
			return false;
		Post post = (Post) o;
		return Objects.equals(this.id, post.id) && Objects.equals(this.title, post.title)
				&& Objects.equals(this.content, post.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.title, this.content);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", title='" + this.title + '\'' + ", content='" + this.content + '\''
				+ '}';
	}

}
