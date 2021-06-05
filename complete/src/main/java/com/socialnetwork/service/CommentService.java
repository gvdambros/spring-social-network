package com.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialnetwork.model.Comment;
import com.socialnetwork.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	KafkaService kafkaService;
	
	public Comment addComment(Comment newComment) {
		kafkaService.asyncValidateComment(newComment);
		return commentRepository.save(newComment);
	}
	
}
