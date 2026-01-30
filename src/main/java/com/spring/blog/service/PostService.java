package com.spring.blog.service;

import com.spring.blog.model.Comment;
import com.spring.blog.model.Post;
import com.spring.blog.repository.CommentRepository;
import com.spring.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    // Create or update a post
    public Post savePost(@NonNull Post post) {
        boolean isNew = post.getId() == null || post.getId().isEmpty();
        if (isNew) {
            post.onCreate();
        } else {
            post.onUpdate();
        }
        return postRepository.save(post);
    }

    private Post enrichWithComments(Post post) {
        if (post == null || post.getId() == null) return post;
        List<Comment> comments = commentRepository.findByPost_Id(post.getId());
        post.setComments(new HashSet<>(comments));
        return post;
    }

    // Get all posts
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        posts.forEach(this::enrichWithComments);
        return posts;
    }

    // Get a post by ID
    public Optional<Post> getPostById(@NonNull String id) {
        return postRepository.findById(id)
                .map(this::enrichWithComments);
    }

    // Delete a post by ID
    public void deletePost(@NonNull String id) {
        postRepository.deleteById(id);
    }
}

