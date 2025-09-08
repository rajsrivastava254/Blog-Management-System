package com.spring.blog.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")  // Mongo equivalent of @Entity + @Table
public class Post {

    @Id
    private String id;  // MongoDB uses String/ObjectId as ID

    private String title;
    private String content;

    @DBRef  // reference to another document instead of @ManyToOne
    private User user;

    @DBRef  // reference list instead of @OneToMany
    private Set<Comment> comments;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // No @PrePersist/@PreUpdate in Mongo, handle manually in service layer
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
