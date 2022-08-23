package com.chunyan.chunyan.vo;

import org.springframework.beans.BeanUtils;

import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.dao.Purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecommendItem {
	private String item_id;
	private String item_name;
	private String item_img;
	private String brand_name;
	private String category_id;
	private String category_name;
	private int price;
	private boolean has_sample;
	private Stats stats;

	public static RecommendItem fromEntity(Item entity) {
		if (entity == null) return null;
		RecommendItem item = new RecommendItem();
		BeanUtils.copyProperties(entity, item);
		return item;
	}
}
