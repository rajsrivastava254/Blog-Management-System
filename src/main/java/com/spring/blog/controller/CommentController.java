package com.spring.blog.controller;

import com.spring.blog.model.Comment;
import com.spring.blog.security.MyUserPrincipal;
import com.spring.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Map<String, String> request) {
        String postId = request.get("postId");
        String content = request.get("content");

        if (postId == null || content == null || postId.isEmpty() || content.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal userPrincipal = (MyUserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUser().getId();

        try {
            Comment comment = commentService.createComment(postId, userId, content);
            return ResponseEntity.status(201).body(comment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable @NonNull String id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable @NonNull String postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable @NonNull String id, 
                                                   @RequestBody Map<String, String> request) {
        String content = request.get("content");
        if (content == null) {
            return ResponseEntity.badRequest().build();
        }

        // Check if user owns the comment
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal userPrincipal = (MyUserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUser().getId();

        if (!commentService.isCommentOwner(id, userId)) {
            return ResponseEntity.status(403).build();
        }

        try {
            Comment updatedComment = commentService.updateComment(id, content);
            return ResponseEntity.ok(updatedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable @NonNull String id) {
        // Check if user owns the comment
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserPrincipal userPrincipal = (MyUserPrincipal) authentication.getPrincipal();
        String userId = userPrincipal.getUser().getId();

        if (!commentService.isCommentOwner(id, userId)) {
            return ResponseEntity.status(403).build();
        }

        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
