package com.spring.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String content;

    @DBRef
    @JsonBackReference
    private Post post;

    @DBRef
    @JsonIgnoreProperties({"password", "posts"})
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonProperty("postId")
    public String getPostId() {
        return post != null ? post.getId() : null;
    }
}
