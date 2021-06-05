package com.socialnetwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialnetwork.exception.NotFoundException;
import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;
import com.socialnetwork.model.User;
import com.socialnetwork.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostService postService;

	public User addPostToUser(String userID, Post newPost) throws NotFoundException {
		return userRepository.findById(userID).map(user -> {
			newPost.setAuthor(userID);
			postService.addPost(newPost);
			user.addPost(newPost);
			userRepository.save(user);
			return user;
		}).orElseThrow(() -> new NotFoundException(userID));
	}

	public User getUser(String userID) {
		return userRepository.findById(userID).map(user -> {
			return user;
		}).orElseThrow(() -> new NotFoundException(userID));
	}
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User addUser(User newUser) {
		return userRepository.save(newUser);
	}

	public Post getPostOfUser(String userID, String postID) throws NotFoundException {
		Post post = postService.getPost(postID);
		if (!post.getAuthor().equals(userID)) {
			throw new NotFoundException(postID);
		}
		return post;
	}

	public List<Post> getPostsOfUser(String userID) throws NotFoundException {
		return userRepository.findById(userID).map(user -> {
			return user.getPosts();
		}).orElseThrow(() -> new NotFoundException(userID));
	}

	public List<Comment> getCommentsOfPost(String userID, String postID) throws NotFoundException {
		return getPostOfUser(userID, postID).getComments();
	}

	public User replaceUser(String userID, User newUser) throws NotFoundException {
		return userRepository.findById(userID).map(user -> {
			user.setName(newUser.getName());
			user.setEmail(newUser.getEmail());
			return userRepository.save(user);
		}).orElseGet(() -> {
			newUser.setId(userID);
			return userRepository.save(newUser);
		});
	}

}
