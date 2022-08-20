package com.chunyan.chunyan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.exception.DuplicateException;
import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.dao.User;
import com.chunyan.chunyan.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@PostMapping
	@RequestMapping("/login")
	public Response<String> login(LoginDto loginDto)  throws NotFoundException {
		if (!userService.validate(loginDto.user_id, loginDto.password)) {
			throw new NotFoundException("Invalid login info");
		}

		return new Response<>(HttpStatus.ACCEPTED.value(), "Login succeed");
	}

	@PostMapping("/user")
	public Response<String> register(@RequestBody UserDto userDto) throws DuplicateException {
		User user = User.fromUserDto(userDto);
		userService.regist(user);

		return new Response<>(HttpStatus.CREATED.value(), "User created");
	}

	@PutMapping("/user")
	public Response<String> updateUser(UserDto userDto) throws NotFoundException {
		User user = User.fromUserDto(userDto);
		userService.update(user);

		return new Response<>(HttpStatus.OK.value(), "User updated");
	}

	@AllArgsConstructor
	@Getter
	@Setter
	private static class LoginDto {
		private String user_id;
		private String password;
	}

	@AllArgsConstructor
	@Getter
	@Setter
	public static class UserDto {
		private String user_id;
		private String password;
		private String gender;
		private String skin_type;
		private Integer age_group;
		private String skin_info;
		private String skin_tone;
	}
}
