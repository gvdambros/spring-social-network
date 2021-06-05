package com.socialnetwork.test.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.socialnetwork.model.Comment;
import com.socialnetwork.model.Post;
import com.socialnetwork.model.User;
import com.socialnetwork.service.CommentService;
import com.socialnetwork.service.PostService;
import com.socialnetwork.service.UserService;

@TestConfiguration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	CommentService commentService;

	@Bean
	CommandLineRunner initDatabase() {

		return args -> {
			User bilbo = new User("Bilbo Baggins", "burglar@shire.com");

			Post bilboPost = new Post("First Post", "This is my first post!!!");

			User frodo = new User("Frodo Baggins", "thief@shire.com");

			Post frodoPost = new Post("First Post", "This is my first post too!!!");

			Comment frodoComment = new Comment(frodo.getId(), "Nice first post!!");

			log.info("Preloading " + userService.addUser(bilbo));
			log.info("Preloading " + userService.addUser(frodo));

			log.info("Preloading " + userService.addPostToUser(bilbo.getId(), bilboPost));
			log.info("Preloading " + userService.addPostToUser(frodo.getId(), frodoPost));
			
			log.info("Preloading " + postService.addComentToPost(bilboPost.getId(), frodoComment));
		};
	}
}
