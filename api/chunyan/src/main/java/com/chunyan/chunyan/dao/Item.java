package com.chunyan.chunyan.dao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.chunyan.chunyan.controller.ItemController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ITEM", schema = "kurly_schema")
public class Item {

	@Id
	private String item_id;

	private String item_name;
	private String item_img;
	private String brand_name;
	private String category_id;
	private int price;
	private boolean has_sample;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", insertable = false, updatable = false)
	private Category category;

	public static Item fromItemDto(ItemController.ItemDto itemDto) {
		Item item = new Item();
		BeanUtils.copyProperties(itemDto, item);
		return item;
	}

}
