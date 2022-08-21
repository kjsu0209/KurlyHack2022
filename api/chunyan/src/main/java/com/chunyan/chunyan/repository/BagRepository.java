package com.chunyan.chunyan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.dao.Review;

@Repository
public interface BagRepository extends CrudRepository<Bag, Integer> {
	@Query("SELECT b FROM Bag b WHERE b.user_id = :user_id AND b.status = 'STORED'")
	List<Bag> findAllByUserId(String user_id);
}
