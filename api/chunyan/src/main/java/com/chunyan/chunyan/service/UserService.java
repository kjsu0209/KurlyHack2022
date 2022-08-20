package com.chunyan.chunyan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chunyan.chunyan.dao.User;
import com.chunyan.chunyan.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

	UserRepository userRepository;

	public boolean validate(String user_id, String password) {
		log.error("long : " + userRepository.count());
		Optional<User> user = userRepository.findById(user_id);

		return !user.isPresent() || user.get().getPassword().equals(password);
	}

}
