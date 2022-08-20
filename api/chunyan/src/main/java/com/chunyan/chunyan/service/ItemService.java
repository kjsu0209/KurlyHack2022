package com.chunyan.chunyan.service;

import java.util.List;

import com.chunyan.chunyan.dao.Item;

public interface ItemService {

	void addItem(Item item);

	List<Item> getItemsByCategory(String categoryId);

	Item getItemById(String item_id);

}
