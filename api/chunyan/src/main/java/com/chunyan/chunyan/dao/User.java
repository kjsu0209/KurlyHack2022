package com.chunyan.chunyan.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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
}
