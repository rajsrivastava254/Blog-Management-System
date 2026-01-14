package com.spring.blog.service;

import com.spring.blog.model.Post;
import com.spring.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // Create or update a post
    public Post savePost(@NonNull Post post) {
        return postRepository.save(post);
    }

    // Get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get a post by ID
    public Optional<Post> getPostById(@NonNull String id) {
        return postRepository.findById(id);
    }

    // Delete a post by ID
    public void deletePost(@NonNull String id) {
        postRepository.deleteById(id);
    }
}

