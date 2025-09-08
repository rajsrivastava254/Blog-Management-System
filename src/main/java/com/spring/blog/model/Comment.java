package com.spring.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments") // replaces @Entity + @Table
public class Comment {

    @Id
    private String id; // Mongo IDs are usually String (ObjectId)

    private String content;

    @DBRef // reference to another document (instead of @ManyToOne)
    private Post post;

    @DBRef
    private User user;

    private LocalDateTime createdAt;
}
