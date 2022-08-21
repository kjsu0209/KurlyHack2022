package com.chunyan.chunyan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.service.RecommendService;
import com.chunyan.chunyan.vo.RecommendItem;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

	private RecommendService recommendService;

	@GetMapping("/sample/{user_id}")
	public List<Item> recommendSampleItems(@PathVariable String user_id) {
		return recommendService.getRecommendSample(user_id);
	}

	@GetMapping("/item/{user_id}")
	public List<RecommendItem> recommendItem(@PathVariable String user_id) {
		return recommendService.getRecommendItem(user_id);
	}
}
