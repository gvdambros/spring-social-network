package com.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;

@Service
public class KafkaService {

	@Autowired
	private KafkaTemplate<Object, Object> template;
	
	void asyncValidatePost(Post newPost) {
		this.template.send("validatePost", newPost);
	}
	
	void asyncValidateComment(Comment newComment) {
		this.template.send("validateComment", newComment);
	}
	
}
