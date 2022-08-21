package com.chunyan.chunyan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chunyan.chunyan.dao.Item;
import com.chunyan.chunyan.vo.RecommendItem;

@Service
public class RecommendServiceImpl implements RecommendService{
	@Override
	public List<Item> getRecommendSample(String user_id) {
		return null;
	}

	@Override
	public List<RecommendItem> getRecommendItem(String user_id) {
		return null;
	}
}
