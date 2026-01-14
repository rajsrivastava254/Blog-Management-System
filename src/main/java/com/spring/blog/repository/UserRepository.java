package com.spring.blog.repository;

import com.spring.blog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	// UserRepository.java
	Optional<User> findByEmailIgnoreCase(String email);
}