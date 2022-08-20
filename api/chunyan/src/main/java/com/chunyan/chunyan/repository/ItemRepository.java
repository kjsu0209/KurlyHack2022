package com.chunyan.chunyan.repository;

import org.springframework.data.repository.CrudRepository;

import com.chunyan.chunyan.dao.Item;

public interface ItemRepository extends CrudRepository<Item, String> {

}
