package com.socialnetwork.model;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Comment {
	
	@Id
	private String id;

	@Indexed
	private String author;
	
	private String content;
	
	@CreatedDate
	private Date createdDate; 
	
	private Date validatedDate; 

	public Comment(String author, String content) {
		this.author = author;
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

	public boolean isValidated() {
		return this.validatedDate != null;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Comment))
			return false;
		Comment post = (Comment) o;
		return Objects.equals(this.id, post.id) && Objects.equals(this.author, post.author)
				&& Objects.equals(this.content, post.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.author, this.content);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", author='" + this.author + '\'' + ", content='" + this.content + '\''
				+ '}';
	}

}
