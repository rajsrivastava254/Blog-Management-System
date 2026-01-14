package com.spring.blog.service;

import com.spring.blog.model.User;
import com.spring.blog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		String normalizedEmail = user.getEmail().trim().toLowerCase();
		user.setEmail(normalizedEmail);
		return userRepository.save(user);
	}

	public Optional<User> getUserById(@NonNull String id) {
		return userRepository.findById(id);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void deleteUser(@NonNull String id) {
		userRepository.deleteById(id);
	}
}