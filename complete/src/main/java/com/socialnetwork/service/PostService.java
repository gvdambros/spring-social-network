package com.socialnetwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialnetwork.exception.NotFoundException;
import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;
import com.socialnetwork.repository.PostRepository;
import com.socialnetwork.validation.PostValidation;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	KafkaService kafkaService;
	
	public Post addComentToPost(String postID, Comment newComment) throws NotFoundException {
		return postRepository.findById(postID).map(post -> {
			commentService.addComment(newComment);
			post.addComment(newComment);
			postRepository.save(post);
			return post;
		}).orElseThrow(() -> new NotFoundException(postID));
	}

	public Post addPost(Post newPost) {
		Post savedPost = postRepository.save(newPost);
		kafkaService.asyncValidatePost(savedPost);
		return savedPost;
	}
	
	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	public Post getPost(String postID) {
		return postRepository.findById(postID).map(post -> {
			return post;
		}).orElseThrow(() -> new NotFoundException(postID));
	}
	
	public List<Comment> getCommentsOfPost(String postID) throws NotFoundException {
		return getPost(postID).getComments();
	}

	public Post validatePost(PostValidation postValidation) {
		String postId = postValidation.getPostId();
		return postRepository.findById(postId).map(post -> {
			post.setValid(postValidation.isValid());
			postRepository.save(post);
			return post;
		}).orElseThrow(() -> new NotFoundException(postId));
	}

	
}
