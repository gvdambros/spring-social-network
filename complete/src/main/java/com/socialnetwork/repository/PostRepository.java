package com.socialnetwork.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.socialnetwork.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {

	Optional<Post> findById(String id);
	
}