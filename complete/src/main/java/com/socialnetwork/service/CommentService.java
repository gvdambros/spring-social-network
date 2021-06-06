package com.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialnetwork.exception.NotFoundException;
import com.socialnetwork.model.Comment;
import com.socialnetwork.repository.CommentRepository;
import com.socialnetwork.validation.CommentValidation;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	KafkaService kafkaService;
	
	public Comment addComment(Comment newComment) {
		Comment savedComment = commentRepository.save(newComment);
		kafkaService.asyncValidateComment(savedComment);
		return savedComment;
	}

	public Comment validateComment(CommentValidation commentValidation) {
		String commentId = commentValidation.getCommentId();
		return commentRepository.findById(commentId).map(comment -> {
			comment.setValid(commentValidation.isValid());
			commentRepository.save(comment);
			return comment;
		}).orElseThrow(() -> new NotFoundException(commentId));
	}
	
}
