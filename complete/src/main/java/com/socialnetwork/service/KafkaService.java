package com.socialnetwork.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;
import com.socialnetwork.validation.CommentValidation;
import com.socialnetwork.validation.PostValidation;

@Service
public class KafkaService {

	private static final Logger log = LoggerFactory.getLogger(KafkaService.class);
	
	@Autowired
	private KafkaTemplate<Object, Object> template;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	void asyncValidatePost(Post newPost) {
		this.template.send("validatePostRequest", newPost);
	}
	
	void asyncValidateComment(Comment newComment) {
		this.template.send("validateCommentRequest", newComment);
	}
	

	@KafkaListener(id = "validatePostResult", topics = "validatePostResult")
	public void validatePost(PostValidation postValidation) {
		log.info("Received: " + postValidation);
		postService.validatePost(postValidation);
	}

	@KafkaListener(id = "validateCommentResult", topics = "validateCommentResult")
	public void validateComment(CommentValidation commentValidation) {
		log.info("Received: " + commentValidation);
		commentService.validateComment(commentValidation);
	}

	@Bean
	public NewTopic createValidatePostResultTopic() {
		return new NewTopic("validatePostResult", 1, (short) 1);
	}

	@Bean
	public NewTopic createValidateCommentResultTopic() {
		return new NewTopic("validateCommentResult", 1, (short) 1);
	}
	
}
