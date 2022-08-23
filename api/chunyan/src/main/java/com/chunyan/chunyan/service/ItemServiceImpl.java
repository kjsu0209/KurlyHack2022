package com.chunyan.chunyan.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chunyan.chunyan.common.exception.NotFoundException;
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
		return itemRepository.findAllByCategory(categoryId);
	}

	@Override
	public Item getItemById(String item_id) throws NotFoundException {
		if (itemRepository.existsById(item_id)) {
			return itemRepository.findById(item_id).get();
		} else {
			throw new NotFoundException("Item not exists");
		}
	}

	@Override
	public List<Item> getItemRankByReview() {
		return itemRepository.rankItemByReview().stream().map(i -> {
			try {
				return getItemById(i);
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}

}
