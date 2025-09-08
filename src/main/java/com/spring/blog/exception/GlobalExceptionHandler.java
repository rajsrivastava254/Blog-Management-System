package com.spring.blog.exception;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String,Object>> handle(Exception ex) {
    return ResponseEntity.status(500).body(Map.of("error", ex.getClass().getSimpleName(), "message", ex.getMessage()));
  }
  
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String,Object>> handleDuplicate(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(Map.of("error","EmailAlreadyExists","message","Email already registered"));
  }
}