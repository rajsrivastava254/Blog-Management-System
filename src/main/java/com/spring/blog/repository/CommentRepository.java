package com.spring.blog.repository;

import com.spring.blog.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    // Custom query methods - use Post_Id for @DBRef Post reference
    List<Comment> findByPost_Id(String postId);
    List<Comment> findByUserId(String userId);
}
