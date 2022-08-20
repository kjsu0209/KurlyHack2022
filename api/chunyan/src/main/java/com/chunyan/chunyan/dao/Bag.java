package com.chunyan.chunyan.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.beans.BeanUtils;

import com.chunyan.chunyan.controller.BagController;
import com.chunyan.chunyan.controller.ReviewController;

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
@Table(name="bag", schema = "kurly_schema")
public class Bag {
	@Id
	@Generated(GenerationTime.INSERT)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bag_id;

	private String user_id;
	private boolean is_sample;
	private String status;
	private int count;
	private LocalDateTime dt;

	public static Bag fromBagDto(BagController.BagDto bagDto) {
		Bag bag = new Bag();
		BeanUtils.copyProperties(bagDto, bag);
		bag.setDt(LocalDateTime.parse(bagDto.getDt(), DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
		return bag;
	}
}
