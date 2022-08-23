package com.chunyan.chunyan.vo;

import java.time.LocalDateTime;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.BeanUtils;

import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.dao.Purchase;
import com.chunyan.chunyan.dao.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ReviewVO {
	private int review_id;
	private String item_id;
	private String purchase_id;
	private int user_rating;
	private String user_id;
	private String gender;
	private String skin_type;
	private int age_group;
	private String skin_info;
	private String skin_tone;
	private String review;
	private boolean is_sample;
	private boolean reordered;
	private LocalDateTime dt;

	public static ReviewVO fromEntity(Review reviewEntity) {
		ReviewVO review = new ReviewVO();
		BeanUtils.copyProperties(reviewEntity, review);
		return review;
	}
}
