package com.chunyan.chunyan.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.exception.DuplicateException;
import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.dao.User;
import com.chunyan.chunyan.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@PostMapping
	@RequestMapping("/login")
	public Response<String> login(@RequestBody LoginDto loginDto)  throws NotFoundException {
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
	public Response<String> updateUser(@RequestBody UserDto userDto) throws NotFoundException {
		User user = User.fromUserDto(userDto);
		userService.update(user);

		return new Response<>(HttpStatus.OK.value(), "User updated");
	}

	@GetMapping("/user")
	public Response<UserInfoDto> getUserInfo(@RequestParam(name = "userId") String user_id) throws NotFoundException {
		User user = userService.getUser(user_id);

		return new Response<>(HttpStatus.OK.value(), UserInfoDto.fromUser(user));
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

	@AllArgsConstructor
	@RequiredArgsConstructor
	@Getter
	@Setter
	public static class UserInfoDto {
		private String user_id;
		private String gender;
		private String skin_type;
		private Integer age_group;
		private String skin_info;
		private String skin_tone;

		public static UserInfoDto fromUser(User user) {
			UserInfoDto userInfoDto = new UserInfoDto();
			BeanUtils.copyProperties(user, userInfoDto);
			return userInfoDto;
		}
	}
}
