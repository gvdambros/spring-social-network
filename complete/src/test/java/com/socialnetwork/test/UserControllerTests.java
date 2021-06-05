/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.socialnetwork.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;
import com.socialnetwork.test.configuration.LoadDatabase;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@Import(LoadDatabase.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void checkGetUsersMethod() throws Exception {
		this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void checkGetUserMethod() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray()).andReturn();

		String userID = JsonPath.read(result.getResponse().getContentAsString(), "$[0].id");

		this.mockMvc.perform(get("/users/" + userID)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(userID)));
	}

	@Test
	public void checkGetPostsMethod() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray()).andReturn();

		String userID = JsonPath.read(result.getResponse().getContentAsString(), "$[0].id");

		this.mockMvc.perform(get("/users/" + userID + "/posts")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray());
	}

	@Test
	public void checkGetPostMethod() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray()).andReturn();

		String userID = JsonPath.read(result.getResponse().getContentAsString(), "$[0].id");

		result = this.mockMvc.perform(get("/users/" + userID + "/posts")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray()).andReturn();
		
		String postID = JsonPath.read(result.getResponse().getContentAsString(), "$[0].id");
		
		this.mockMvc.perform(get("/users/" + userID + "/posts/" + postID)).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(postID)));
	}

	@Test
	public void checkUnknownGetUserMethod() throws Exception {
		this.mockMvc.perform(get("/users/123")).andDo(print()).andExpect(status().isNotFound());
	}

}
