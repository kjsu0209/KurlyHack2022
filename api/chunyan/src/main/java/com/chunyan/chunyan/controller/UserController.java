package com.chunyan.chunyan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.response.Response;
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
	public Response<String> login(LoginDto loginDto) {
		HttpStatus status = HttpStatus.ACCEPTED;
		String result = "Login succeed";
		if (!userService.validate(loginDto.user_id, loginDto.password)) {
			status = HttpStatus.UNAUTHORIZED;
			result = "Invalid login info";
		}

		return new Response<>(status.value(), result);
	}

	@AllArgsConstructor
	@Getter
	@Setter
	private static class LoginDto {
		private String user_id;
		private String password;
	}
}
