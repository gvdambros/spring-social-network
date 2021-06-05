package com.socialnetwork.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.socialnetwork.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findById(String id);
	
	Optional<User> findByEmail(String email);
}