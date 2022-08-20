package com.chunyan.chunyan.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.common.exception.NotFoundException;
import com.chunyan.chunyan.common.response.Response;
import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.service.ItemService;
import com.chunyan.chunyan.service.ItemServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/item")
@RestController
@AllArgsConstructor
public class ItemController {

	private ItemService itemService;

	@PostMapping
	public Response<String> addItem(@RequestBody ItemDto itemDto) {
		Item item = Item.fromItemDto(itemDto);
		itemService.addItem(item);

		return new Response<>(HttpStatus.CREATED.value(), "Item created");
	}

	@GetMapping("/{item_id}")
	public Response<ItemDto> findItemById(@PathVariable(name="item_id") String item_id) throws NotFoundException {
		Item item = itemService.getItemById(item_id);
		ItemDto itemDto = new ItemDto();
		BeanUtils.copyProperties(item, itemDto);
		itemDto.setCategory_name(item.getCategory().getCategory_name());
		return new Response<>(HttpStatus.OK.value(), itemDto);
	}

	@GetMapping("/category/{category_id}")
	public Response<List<ItemDto>> findItemByCategory(@PathVariable(name="category_id") String category_id) {
		List<Item> items = itemService.getItemsByCategory(category_id);

		List<ItemDto> itemList = items.stream().map(i -> {
			ItemDto newItem = new ItemDto();
			BeanUtils.copyProperties(i, newItem);
			newItem.setCategory_name(i.getCategory().getCategory_name());
			return newItem;
		}).collect(Collectors.toList());

		return new Response<>(HttpStatus.OK.value(), itemList);
	}

	@Getter
	@Setter
	@RequiredArgsConstructor
	@AllArgsConstructor
	public static class ItemDto {
		private String item_id;
		private String item_name;
		private String item_img;
		private String brand_name;
		private String category_id;
		private String category_name;
		private int price;
		private boolean has_sample;
	}

}
