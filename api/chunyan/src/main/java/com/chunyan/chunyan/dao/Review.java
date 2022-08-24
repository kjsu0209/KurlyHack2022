package com.chunyan.chunyan.dao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.BeanUtils;

import com.chunyan.chunyan.controller.ItemController;
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
@Table(name="review", schema = "kurly_schema")
public class Review {
	@Id
	@Generated(GenerationTime.INSERT)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int review_id;

	@NotFound(action= NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	private Item item;

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

	public static Review fromReviewDto(ReviewController.ReviewDto reviewDto) {
		Review review = new Review();
		BeanUtils.copyProperties(reviewDto, review);
		return review;
	}
}
