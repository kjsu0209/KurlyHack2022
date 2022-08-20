package com.chunyan.chunyan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.repository.ItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

	private ItemRepository itemRepository;

	@Override
	public void addItem(Item item) {
		itemRepository.save(item);
	}

	@Override
	public List<Item> getItemsByCategory(String categoryId) {
		return null;
	}

	@Override
	public Item getItemById(String item_id) {
		return null;
	}

}
