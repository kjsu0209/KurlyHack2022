package com.chunyan.chunyan.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chunyan.chunyan.controller.UserController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="USER", schema = "kurly_schema")
public class User {
	@Id
	private String user_id;

	private String password;
	private String gender;
	private String skin_type;
	private int age_group;
	private String skin_info;
	private String skin_tone;

	public static User fromUserDto(UserController.UserDto userDto) {
		return User.builder().user_id(userDto.getUser_id())
			.password(userDto.getPassword())
			.age_group(userDto.getAge_group())
			.gender(userDto.getGender())
			.skin_info(userDto.getSkin_info())
			.skin_tone(userDto.getSkin_tone())
			.skin_type(userDto.getSkin_type())
			.build();
	}
}
