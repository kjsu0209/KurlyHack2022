package com.chunyan.chunyan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.chunyan.chunyan.dao.Review;

public interface ReviewRepository extends CrudRepository<Review, String> {

	@Query("SELECT r FROM Review r WHERE r.user_id = :user_id")
	List<Review> findAllByUserId(String user_id);

	@Query("SELECT r FROM Review r WHERE r.item_id = :item_id")
	List<Review> findAllByItemId(String item_id);

	@Query("SELECT r FROM Review r WHERE r.item_id = :item_id AND r.user_id = :user_id")
	List<Review> findAllByItemIdAndUserId(String item_id, String user_id);

}
