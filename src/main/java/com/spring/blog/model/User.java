package com.spring.blog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users") // Mongo equivalent of @Entity + @Table
public class User {

	@Id
	private String id; // Mongo IDs are usually String/ObjectId

	private String email;
	private String password;

	private LocalDateTime createdAt = LocalDateTime.now(); // replaces @CreationTimestamp

	@DBRef // references posts (you can also embed instead of referencing)
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();
}
