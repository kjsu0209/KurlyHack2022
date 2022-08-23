package com.chunyan.chunyan.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chunyan.chunyan.common.exception.DuplicateException;
import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.dao.User;
import com.chunyan.chunyan.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

	UserRepository userRepository;

	public boolean validate(String user_id, String password)  throws NotFoundException{
		User user = getUser(user_id);

		return user.getPassword().equals(password);
	}

	public void regist(User user) throws DuplicateException {
		if (userRepository.existsById(user.getUser_id())) {
			throw new DuplicateException("Duplicated user id");
		}

		userRepository.save(user);
	}

	public void update(User newUser)  throws NotFoundException{
		User user = getUser(newUser.getUser_id());
		if (!ObjectUtils.isEmpty(newUser.getPassword()) && !newUser.getPassword().equals(user.getPassword())) {
			user.setPassword(newUser.getPassword());
		}
		if (!ObjectUtils.isEmpty(newUser.getGender()) && !newUser.getGender().equals(user.getGender())) {
			user.setGender(newUser.getGender());
		}
		if (!ObjectUtils.isEmpty(newUser.getSkin_info()) && !newUser.getSkin_info().equals(user.getSkin_info())) {
			user.setSkin_info(newUser.getSkin_info());
		}
		if (!ObjectUtils.isEmpty(newUser.getSkin_tone()) && !newUser.getSkin_tone().equals(user.getSkin_tone())) {
			user.setPassword(newUser.getPassword());
		}
		if (!ObjectUtils.isEmpty(newUser.getSkin_info()) && !newUser.getSkin_info().equals(user.getSkin_info())) {
			user.setSkin_info(newUser.getSkin_info());
		}
		if (user.getAge_group() != newUser.getAge_group()) {
			user.setAge_group(newUser.getAge_group());
		}

		userRepository.save(user);
	}

	public User getUser(String user_id)  throws NotFoundException{
		Optional<User> user = userRepository.findById(user_id);

		if (!user.isPresent()) {
			throw new NotFoundException("User not found");
		}

		return user.get();
	}


}
