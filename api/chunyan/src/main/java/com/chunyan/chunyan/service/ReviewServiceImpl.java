package com.chunyan.chunyan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chunyan.chunyan.dao.Review;
import com.chunyan.chunyan.repository.ReviewRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService{

	ReviewRepository reviewRepository;

	@Override
	public List<Review> getReviewByUserId(String user_id) {
		return reviewRepository.findAllByUserId(user_id);
	}

	@Override
	public List<Review> getReviewByItemId(String item_id) {
		return reviewRepository.findAllByItemId(item_id);
	}

	@Override
	public List<Review> getReviewByItemIdAndUserId(String item_id, String user_id) {
		return reviewRepository.findAllByItemIdAndUserId(item_id, user_id);
	}

	@Override
	public void addReview(Review review) {
		reviewRepository.save(review);
	}
}
