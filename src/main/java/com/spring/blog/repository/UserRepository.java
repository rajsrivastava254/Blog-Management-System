package com.spring.blog.repository;

import com.spring.blog.model.User;

import java.util.Optional;


//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
	// UserRepository.java
	@Query("select count(u)>0 from User u where lower(u.email) = lower(:email)")
	boolean existsByEmailIgnoreCase(@Param("email") String email);

	Optional<User> findByEmailIgnoreCase(String email);
}