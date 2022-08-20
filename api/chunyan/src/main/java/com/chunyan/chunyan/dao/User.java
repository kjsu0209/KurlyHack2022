package com.chunyan.chunyan.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

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
	private Integer age_group;
	private String skin_info;
	private String skin_tone;

	public static User fromUserDto(UserController.UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		return user;
	}
}
