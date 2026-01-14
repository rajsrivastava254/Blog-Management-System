package com.spring.blog.repository;

import com.spring.blog.model.Comment;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    // JpaRepository provides CRUD operations and pagination support by default

    // Custom query methods (if needed) can be added here
}
