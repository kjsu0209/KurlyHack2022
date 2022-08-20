package com.chunyan.chunyan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chunyan.chunyan.dao.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {

	@Query(value = "SELECT i FROM Item i WHERE i.category_id = :category_id")
	List<Item> findAllByCategory(String category_id);
}
