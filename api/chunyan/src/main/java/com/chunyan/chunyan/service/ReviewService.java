package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.dao.Review;

public interface ReviewService {

	List<Review> getReviewByUserId(String user_id);

	List<Review> getReviewByItemId(String item_id);

	List<Review> getReviewByItemIdAndUserId(String item_id, String user_id);

	void addReview(Review review);
}
