package com.socialnetwork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.socialnetwork.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

	Optional<Comment> findById(String id);
	
	List<Comment> findByAuthor(String author);
	
}