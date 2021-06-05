package com.socialnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.socialnetwork.exception.NotFoundException;
import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;
import com.socialnetwork.model.User;
import com.socialnetwork.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/users")
	List<User> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/users/{userID}")
	User getUser(@PathVariable String userID) throws NotFoundException {
		return userService.getUser(userID);
	}
	
	@GetMapping("/users/{userID}/posts")
	List<Post> getPosts(@PathVariable String userID) throws NotFoundException {
		return userService.getPostsOfUser(userID);
	}

	@GetMapping("/users/{userID}/posts/{postID}")
	Post getPost(@PathVariable String userID, @PathVariable String postID) throws NotFoundException {
		return userService.getPostOfUser(userID, postID);
	}

	@GetMapping("/users/{userID}/posts/{postID}/comments")
	List<Comment> getComments(@PathVariable String userID, @PathVariable String postID) throws NotFoundException {
		return userService.getCommentsOfPost(userID, postID);
	}

	@PostMapping("/users")
	User newUser(@RequestBody User newUser) {
		return userService.addUser(newUser);
	}

	@PutMapping("/users/{userID}")
	User replaceUser(@RequestBody User newUser, @PathVariable String userID) {
		return userService.replaceUser(userID, newUser);
	}

}
