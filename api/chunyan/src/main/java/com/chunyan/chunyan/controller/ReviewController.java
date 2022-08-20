package com.chunyan.chunyan.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.dao.Review;
import com.chunyan.chunyan.service.ReviewService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private ReviewService reviewService;

	@PostMapping
	public Response<String> addReview(@RequestBody ReviewDto reviewDto) {
		Review review = Review.fromReviewDto(reviewDto);
		reviewService.addReview(review);

		return new Response<>(HttpStatus.CREATED.value(), "Review inserted");
	}

	@GetMapping("/{item_id}")
	public Response<List<Review>> findByItemId(@PathVariable String item_id) {
		List<Review> reviews = reviewService.getReviewByItemId(item_id);

		return new Response<>(HttpStatus.OK.value(), reviews);
	}

	@GetMapping("/user/{user_id}")
	public Response<List<Review>> findByUserId(@PathVariable String user_id) {
		List<Review> reviews = reviewService.getReviewByUserId(user_id);

		return new Response<>(HttpStatus.OK.value(), reviews);
	}

	@RequiredArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public static class ReviewDto {
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
	}
}
