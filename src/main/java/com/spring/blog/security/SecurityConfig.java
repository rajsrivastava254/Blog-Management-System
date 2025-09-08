package com.spring.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	  http.csrf(csrf -> csrf.disable())
    	      .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    	  return http.build();
        
    }
}
