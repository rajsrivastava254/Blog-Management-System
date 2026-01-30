package com.spring.blog.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @JsonAlias({"Email", "EMAIL"})
    private String email;
    @JsonAlias({"Password", "PASSWORD"})
    private String password;
}
