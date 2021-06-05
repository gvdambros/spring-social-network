package com.socialnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.socialnetwork.exception.NotFoundException;
import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;
import com.socialnetwork.service.PostService;

@RestController
public class PostController {

	@Autowired
	PostService postService;

	@GetMapping("/posts")
	List<Post> getPosts() {
		return postService.getPosts();
	}

	@GetMapping("/posts/{postID}")
	Post getPost(@PathVariable String postID) throws NotFoundException {
		return postService.getPost(postID);
	}

	@GetMapping("/posts/{postID}/comments")
	List<Comment> getComments(@PathVariable String postID) throws NotFoundException {
		return postService.getCommentsOfPost(postID);
	}

	@PostMapping("/posts")
	Post newPost(@RequestBody Post newPost) {
		return postService.addPost(newPost);
	}

}
