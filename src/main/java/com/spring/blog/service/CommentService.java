package com.spring.blog.service;

import com.spring.blog.model.Comment;
import com.spring.blog.model.Post;
import com.spring.blog.model.User;
import com.spring.blog.repository.CommentRepository;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(@NonNull String postId, @NonNull String userId, @NonNull String content) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (postOpt.isEmpty()) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        Post post = postOpt.get();
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(userOpt.get());
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        // Add comment to post's comments so it appears when fetching posts
        if (post.getComments() == null) {
            post.setComments(new java.util.HashSet<>());
        }
        post.getComments().add(savedComment);
        postRepository.save(post);

        return savedComment;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(@NonNull String id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getCommentsByPostId(@NonNull String postId) {
        return commentRepository.findByPost_Id(postId);
    }

    public Comment updateComment(@NonNull String id, @NonNull String content) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            throw new RuntimeException("Comment not found with id: " + id);
        }

        Comment comment = commentOpt.get();
        comment.setContent(content);
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(@NonNull String id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            throw new RuntimeException("Comment not found with id: " + id);
        }
        Comment comment = commentOpt.get();
        Post post = comment.getPost();
        if (post != null && post.getComments() != null) {
            post.getComments().removeIf(c -> c.getId() != null && c.getId().equals(id));
            postRepository.save(post);
        }
        commentRepository.deleteById(id);
    }

    public boolean isCommentOwner(@NonNull String commentId, @NonNull String userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) {
            return false;
        }
        Comment comment = commentOpt.get();
        return comment.getUser() != null && comment.getUser().getId().equals(userId);
    }
}
