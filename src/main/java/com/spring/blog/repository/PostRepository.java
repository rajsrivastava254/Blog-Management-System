package com.spring.blog.repository;

import com.spring.blog.model.Post;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    // JpaRepository provides CRUD, pagination, and custom query support by default
}
