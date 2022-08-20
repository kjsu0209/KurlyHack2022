package com.chunyan.chunyan.dao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

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
	private int review_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	private Item item;

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
}
