package com.socialnetwork.test.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;
import com.socialnetwork.model.User;
import com.socialnetwork.service.CommentService;
import com.socialnetwork.service.PostService;
import com.socialnetwork.service.UserService;
import com.socialnetwork.validation.CommentValidation;
import com.socialnetwork.validation.PostValidation;

@TestConfiguration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	private String bilboId;
	
	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;
	
	@Autowired
	private KafkaTemplate<Object, Object> template;

	@Bean
	CommandLineRunner initDatabase() {

		return args -> {
			User bilbo = userService.addUser(new User("Bilbo Baggins", "burglar@shire.com"));
			bilboId = bilbo.getId();
			
			User frodo = userService.addUser(new User("Frodo Baggins", "thief@shire.com"));

			log.info("Preloading " + bilbo);
			log.info("Preloading " + frodo);

			Post bilboPost = new Post("First Post", "This is my first post!!!");
			
			Post frodoPost = new Post("First Post", "This is my first post too!!!");

			Comment frodoComment = new Comment(frodo.getId(), "Nice first post!!");


			log.info("Preloading " + userService.addPostToUser(bilbo.getId(), bilboPost));
			log.info("Preloading " + userService.addPostToUser(frodo.getId(), frodoPost));

			log.info("Preloading " + postService.addComentToPost(bilboPost.getId(), frodoComment));
		};
	}

	@KafkaListener(id = "validatePostRequest", topics = "validatePostRequest")
	public void validatePost(Post post) {
		log.info("Received: " + post);
		template.send("validatePostResult", new PostValidation(post.getId(), post.getAuthor().equals(bilboId)));
	}

	@KafkaListener(id = "validateCommentRequest", topics = "validateCommentRequest")
	public void validateComment(Comment comment) {
		log.info("Received: " + comment);
		template.send("validateCommentResult", new CommentValidation(comment.getId(), comment.getAuthor().equals(bilboId)));
	}

	@Bean
	public NewTopic createValidatePostRequestTopic() {
		return new NewTopic("validatePostRequest", 1, (short) 1);
	}

	@Bean
	public NewTopic createValidateCommentRequestTopic() {
		return new NewTopic("validateCommentRequest", 1, (short) 1);
	}

}
